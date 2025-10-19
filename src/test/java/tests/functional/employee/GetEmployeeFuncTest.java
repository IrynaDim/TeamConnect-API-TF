package tests.functional.employee;

import constants.testData.UserTestData;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import model.dto.user.UserResponse;
import org.testng.annotations.Test;
import steps.EmployeeSteps;
import tests.BaseConfiguration;
import constants.TestGroup;
import fixture.FixtureLoader;

import static constants.testData.CommonTestData.NON_EXISTENT_ID;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Epic("Functional tests")
@Feature("Employees")
public class GetEmployeeFuncTest extends BaseConfiguration {
    private final EmployeeSteps employeeSteps = new EmployeeSteps(this::spec);

    @Issue("FUNC-01")
    @Test(groups = TestGroup.FUNCTIONAL)
    public void shouldReturnEmployee_whenEmployeeExists() {
        UserResponse actual = employeeSteps.getById(UserTestData.EMPLOYEE_11.getUserId(), SC_OK);

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(FixtureLoader.findOne(UserResponse.class, UserResponse::getId, UserTestData.EMPLOYEE_11.getUserId()));
    }

    @Issue("FUNC-02")
    @Test(groups = TestGroup.FUNCTIONAL)
    public void shouldReturn404_whenEmployeeDoesNotExist() {
        employeeSteps.getById(NON_EXISTENT_ID, SC_NOT_FOUND);
    }

}
