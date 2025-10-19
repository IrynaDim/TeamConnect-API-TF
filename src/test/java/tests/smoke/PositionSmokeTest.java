package tests.smoke;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import org.testng.annotations.Test;
import steps.PositionSteps;
import tests.BaseConfiguration;
import constants.TestGroup;

import static org.apache.http.HttpStatus.SC_OK;

@Epic("Smoke tests")
@Feature("Position")
public class PositionSmokeTest extends BaseConfiguration {
    private final PositionSteps steps = new PositionSteps(this::spec);

    @Issue("SMK-08")
    @Test(groups = TestGroup.SMOKE)
    public void shouldReturn200_whenGetPositionsWithValidToken() {
        steps.getAll(SC_OK);
    }
}
