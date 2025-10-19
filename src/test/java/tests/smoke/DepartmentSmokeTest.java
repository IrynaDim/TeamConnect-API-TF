package tests.smoke;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import org.testng.annotations.Test;
import steps.DepartmentSteps;
import tests.BaseConfiguration;
import constants.TestGroup;

import static org.apache.http.HttpStatus.SC_OK;

@Epic("Smoke tests")
@Feature("Department")
public class DepartmentSmokeTest extends BaseConfiguration {
    private final DepartmentSteps steps = new DepartmentSteps(this::spec);

    @Issue("SMK-04")
    @Test(groups = TestGroup.SMOKE)
    public void shouldReturn200_whenGetDepartmentsWithValidToken() {
        steps.getAll(SC_OK);
    }
}
