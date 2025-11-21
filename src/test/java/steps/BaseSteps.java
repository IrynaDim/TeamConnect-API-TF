package steps;

import io.qameta.allure.Allure;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public abstract class BaseSteps {

    private final Supplier<RequestSpecification> spec;

    protected BaseSteps(Supplier<RequestSpecification> spec) {
        this.spec = spec;
    }

    protected RequestSpecification spec() {
        return spec.get();
    }

    public <T> T post(String path, Object body, int expectedStatus, Class<T> responseType) {
        return Allure.step("POST " + path + " (expected " + expectedStatus + ")", () -> {
            ValidatableResponse response = spec()
                    .body(body)
                    .post(path)
                    .then()
                    .statusCode(expectedStatus);

            return expectedStatus == HttpStatus.SC_OK
                    ? response.extract().as(responseType)
                    : null;
        });
    }

    public <T> T get(String path, int expectedStatus, Class<T> responseType) {
        return Allure.step("GET " + path + " (expected " + expectedStatus + ")", () -> {
            ValidatableResponse response = spec()
                    .get(path)
                    .then()
                    .statusCode(expectedStatus);

            return expectedStatus == HttpStatus.SC_OK
                    ? response.extract().as(responseType)
                    : null;
        });
    }

    public <T> List<T> getList(String path, int expectedStatus) {
        return Allure.step("GET list " + path + " (expected " + expectedStatus + ")", () -> {
            ValidatableResponse response = spec()
                    .get(path)
                    .then()
                    .statusCode(expectedStatus);

            return expectedStatus == HttpStatus.SC_OK
                    ? response.extract().as(new TypeRef<>() {
            })
                    : null;
        });
    }

    public void delete(String path, int expectedStatus) {
        Allure.step("DELETE " + path + " (expected " + expectedStatus + ")", () -> {
            spec()
                    .delete(path)
                    .then()
                    .statusCode(expectedStatus);
        });
    }

    public <T> List<T> getListWithParams(String path, Map<String, Object> params, int expectedStatus) {
        return Allure.step("GET " + path + " with params (expected " + expectedStatus + ")", () -> {

            RequestSpecification request = spec().basePath(path);

            if (params != null && !params.isEmpty()) {
                params.forEach(request::queryParam);
            }

            ValidatableResponse response = request.get()
                    .then()
                    .statusCode(expectedStatus);

            return expectedStatus == HttpStatus.SC_OK
                    ? response.extract().as(new TypeRef<>() {
            })
                    : null;
        });
    }

    public void patch(String path, Object body, int expectedStatus) {
        Allure.step("PATCH " + path + " (expected " + expectedStatus + ")", () -> {
            spec()
                    .body(body)
                    .patch(path)
                    .then()
                    .statusCode(expectedStatus);
        });
    }

    public void deleteSilently(String path) {
        Allure.step("DELETE " + path + " (silent)", () -> {
            try {
                spec()
                        .delete(path)
                        .then()
                        .extract()
                        .response();
            } catch (Exception ignored) {}
        });
    }
}
