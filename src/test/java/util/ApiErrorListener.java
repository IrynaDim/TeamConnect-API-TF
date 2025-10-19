package util;

import io.qameta.allure.Allure;
import io.restassured.response.Response;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ApiErrorListener implements ITestListener {

    private static final ThreadLocal<Response> lastResponse = new ThreadLocal<>();

    public static void setLastResponse(Response response) {
        lastResponse.set(response);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        Response r = lastResponse.get();
        if (r != null) {
            Allure.addAttachment("API Error",
                    String.format("Status: %s%nMessage: %s%nURL: %s",
                            r.statusCode(),
                            r.jsonPath().getString("message"),
                            r.jsonPath().getString("url")));
            Allure.addAttachment("Response JSON", "application/json", r.asPrettyString());
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        lastResponse.remove();
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        lastResponse.remove();
    }

}
