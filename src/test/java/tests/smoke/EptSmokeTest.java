package tests.smoke;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import org.testng.annotations.Test;
import steps.EptSteps;
import tests.BaseConfiguration;
import constants.TestGroup;

import static org.apache.http.HttpStatus.SC_OK;

@Epic("Smoke tests")
@Feature("Employee Project Task")
public class EptSmokeTest extends BaseConfiguration {
    private final EptSteps steps = new EptSteps(this::spec);

    @Issue("SMK-03")
    @Test(groups = TestGroup.SMOKE)
    public void shouldReturn200_whenGetEptForCurrentUser() {
        steps.getForCurrentUser(SC_OK);
    }
}
