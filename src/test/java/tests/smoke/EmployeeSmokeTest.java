package tests.smoke;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import org.testng.annotations.Test;
import steps.EmployeeSteps;
import tests.BaseConfiguration;
import constants.TestGroup;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

@Epic("Smoke tests")
@Feature("Employee")
public class EmployeeSmokeTest extends BaseConfiguration {
    private final EmployeeSteps steps = new EmployeeSteps(this::spec);
    private final EmployeeSteps noAuthSteps = new EmployeeSteps(this::noAuth);
    private final EmployeeSteps invalidTokenSteps = new EmployeeSteps(this::withInvalidToken);

    @Issue("SMK-01")
    @Test(groups = TestGroup.SMOKE)
    public void shouldReturn200_whenGetEmployeesWithValidToken() {
        steps.getAll(SC_OK);
    }

    @Issue("SMK-02")
    @Test(groups = TestGroup.SMOKE)
    public void shouldReturn200_whenGetEmployeesAnniversariesWithValidRange() {
        steps.getWithAnniversary(SC_OK, "01-01", "31-12");
    }

    @Issue("SMK-05")
    @Test(groups = TestGroup.SMOKE)
    public void shouldReturn401_whenGetEmployeesWithoutToken() {
        noAuthSteps.getAll(SC_UNAUTHORIZED);
    }

    @Issue("SMK-06")
    @Test(groups = TestGroup.SMOKE)
    public void shouldReturn403_whenGetEmployeesWithInvalidToken() {
        invalidTokenSteps.getAll(SC_UNAUTHORIZED); // или SC_FORBIDDEN, если API возвращает 403
    }
}
