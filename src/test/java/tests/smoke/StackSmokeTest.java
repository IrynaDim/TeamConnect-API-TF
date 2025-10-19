package tests.smoke;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import org.testng.annotations.Test;
import steps.StackSteps;
import tests.BaseConfiguration;
import constants.TestGroup;

import static org.apache.http.HttpStatus.SC_OK;

@Epic("Smoke tests")
@Feature("Stack")
public class StackSmokeTest extends BaseConfiguration {
    private final StackSteps steps = new StackSteps(this::spec);

    @Issue("SMK-07")
    @Test(groups = TestGroup.SMOKE)
    public void shouldReturn200_whenGetStacksWithValidToken() {
        steps.getAll(SC_OK);
    }
}
