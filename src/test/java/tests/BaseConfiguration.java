package tests;

import annotations.Auth;
import constants.AuthParameters;
import constants.Routes;
import io.qameta.allure.restassured.AllureRestAssured;
import io.qameta.allure.testng.AllureTestNg;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;
import model.enums.Role;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import util.ApiErrorListener;
import util.EnvConfig;

import java.lang.reflect.Method;

import static io.restassured.http.ContentType.JSON;
import static org.apache.http.HttpStatus.SC_OK;

@Slf4j
@Listeners({AllureTestNg.class, ApiErrorListener.class})
public abstract class BaseConfiguration {

    private static RequestSpecification BASE_SPEC;
    private static final TokenProvider TOKENS = new TokenProvider();
    private final ThreadLocal<AuthParameters> currentAuth = new ThreadLocal<>();

    @BeforeSuite(alwaysRun = true)
    public void setup() {
        BASE_SPEC = new RequestSpecBuilder()
                .setBaseUri(EnvConfig.apiBaseUrl())
                .setContentType(JSON)
                .setAccept(JSON)
                .addFilter(new AllureRestAssured())
                .addFilter((req, res, ctx) -> {
                    Response response = ctx.next(req, res);
                    ApiErrorListener.setLastResponse(response);
                    return response;
                })
                .build();

        log.info("Base spec initialized for {}", EnvConfig.apiBaseUrl());

        checkHealth();
    }

    @BeforeMethod(alwaysRun = true)
    public void initAuth(Method method) {
        Auth auth = method.getAnnotation(Auth.class);
        if (auth == null) auth = method.getDeclaringClass().getAnnotation(Auth.class);

        currentAuth.set(auth == null ? AuthParameters.EMPLOYEE : auth.value());
    }

    @AfterMethod(alwaysRun = true)
    public void clearAuth() {
        currentAuth.remove();
    }

    protected RequestSpecification spec() {
        AuthParameters auth = currentAuth.get();

        return switch (auth) {
            case NO_AUTH -> RestAssured.given().spec(BASE_SPEC);
            case INVALID_TOKEN -> RestAssured.given()
                    .spec(BASE_SPEC)
                    .header("Authorization", TOKENS.invalidBearer());
            default -> RestAssured.given()
                    .spec(BASE_SPEC)
                    .header("Authorization", TOKENS.bearer(mapToRole(auth)));
        };
    }

    private Role mapToRole(AuthParameters auth) {
        return switch (auth) {
            case EMPLOYEE -> Role.EMPLOYEE;
            case PM -> Role.PM;
            case HR -> Role.HR;
            case ADMIN -> Role.ADMIN;
            default -> throw new IllegalArgumentException("Invalid role: " + auth);
        };
    }

    protected void checkHealth() {
        log.info("Performing API health check...");
        RestAssured.given()
                .spec(BASE_SPEC)
                .basePath(Routes.Health.HEALTH.getPath())
                .get()
                .then()
                .statusCode(SC_OK);
        log.info("Health check passed successfully");
    }
}
