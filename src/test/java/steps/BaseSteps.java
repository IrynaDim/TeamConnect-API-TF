package steps;

import io.qameta.allure.Step;
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

    @Step("POST {path} (expected {expectedStatus})")
    protected <T> T post(String path, Object body, int expectedStatus, Class<T> responseType) {
        ValidatableResponse response = spec()
                .body(body)
                .post(path)
                .then()
                .statusCode(expectedStatus);

        return expectedStatus == HttpStatus.SC_OK
                ? response.extract().as(responseType)
                : null;
    }

    @Step("GET {path} (expected {expectedStatus})")
    protected <T> T get(String path, int expectedStatus, Class<T> responseType) {
        ValidatableResponse response = spec()
                .get(path)
                .then()
                .statusCode(expectedStatus);

        return expectedStatus == HttpStatus.SC_OK
                ? response.extract().as(responseType)
                : null;
    }

    @Step("GET list {path} (expected {expectedStatus})")
    protected <T> List<T> getList(String path, int expectedStatus, Class<T> elementType) {
        ValidatableResponse response = spec()
                .get(path)
                .then()
                .statusCode(expectedStatus);

        return expectedStatus == HttpStatus.SC_OK
                ? response.extract().as(new TypeRef<>() {})
                : null;
    }

    @Step("DELETE {path} (expected {expectedStatus})")
    protected void delete(String path, int expectedStatus) {
        spec()
                .delete(path)
                .then()
                .statusCode(expectedStatus);
    }

    @Step("GET {path} with params (expected {expectedStatus})")
    protected <T> List<T> getListWithParams(String path, Map<String, Object> params, int expectedStatus) {
        RequestSpecification request = spec().basePath(path);
        if (params != null && !params.isEmpty()) {
            for (var entry : params.entrySet()) {
                request.queryParam(entry.getKey(), entry.getValue());
            }
        }

        ValidatableResponse response = request.get()
                .then()
                .statusCode(expectedStatus);

        return expectedStatus == 200
                ? response.extract().as(new TypeRef<>() {
        })
                : null;
    }

    @Step("PATCH {path} (expected {expectedStatus})")
    protected void patch(String path, Object body, int expectedStatus) {
        spec()
                .body(body)
                .when().patch(path)
                .then().statusCode(expectedStatus);
    }

    @Step("DELETE {path} (no status check)")
    protected void deleteSilently(String path) {
        try {
            spec()
                    .delete(path)
                    .then()
                    .extract()
                    .response();
        } catch (Exception ignored) {

        }
    }

}
