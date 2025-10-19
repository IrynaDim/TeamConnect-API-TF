package tests;

import annotations.AuthUser;
import constants.Routes;
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
    private final ThreadLocal<Role> currentRole = new ThreadLocal<>();

    @BeforeSuite(alwaysRun = true)
    public void setup() {
        BASE_SPEC = new RequestSpecBuilder()
                .setBaseUri(EnvConfig.apiBaseUrl())
                .setContentType(JSON)
                .setAccept(JSON)
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
    public void initRole(Method method) {
        AuthUser auth = method.getAnnotation(AuthUser.class);
        if (auth == null) auth = method.getDeclaringClass().getAnnotation(AuthUser.class);
        currentRole.set(auth == null ? Role.EMPLOYEE : auth.value());
    }

    @AfterMethod(alwaysRun = true)
    public void clearRole() {
        currentRole.remove();
    }

    protected RequestSpecification spec() {
        return RestAssured.given()
                .spec(BASE_SPEC)
                .header("Authorization", TOKENS.bearer(currentRole.get()));
    }

    protected RequestSpecification noAuth() {
        return RestAssured.given().spec(BASE_SPEC);
    }

    protected RequestSpecification withInvalidToken() {
        return RestAssured.given()
                .spec(BASE_SPEC)
                .header("Authorization", TOKENS.invalidBearer());
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
