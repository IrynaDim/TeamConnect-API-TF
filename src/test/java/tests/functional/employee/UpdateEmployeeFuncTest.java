package tests.functional.employee;

import factory.UserUpdateDtoFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import model.dto.user.UserCurrentResponse;
import model.dto.user.UserUpdateRequest;
import org.testng.annotations.*;
import steps.EmployeeSteps;
import tests.BaseConfiguration;
import constants.TestGroup;
import util.JsonUtils;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Epic("Functional tests")
@Feature("Employees")
@Slf4j
public class UpdateEmployeeFuncTest extends BaseConfiguration {

    private final EmployeeSteps employeeSteps = new EmployeeSteps(this::spec);

    private UserCurrentResponse employeeBeforeUpdate;

    @BeforeMethod(alwaysRun = true)
    @Step("Saving current employee state before test")
    public void saveCurrentEmployeeState() {
        employeeBeforeUpdate = employeeSteps.getCurrent(SC_OK);
    }

    @AfterMethod(alwaysRun = true)
    @Step("Restoring employee state  in DB after test")
    public void restoreEmployeeState() {
        if (employeeBeforeUpdate != null) {
            try {
                UserUpdateRequest restoreRequest =
                        JsonUtils.mapper().convertValue(employeeBeforeUpdate, UserUpdateRequest.class);
                employeeSteps.update(restoreRequest, SC_OK);
            } catch (RuntimeException e) {
                log.error("Failed to restore employee state", e);
            }
        }
    }

    @DataProvider(name = "invalidEmployeeData")
    public Object[][] invalidEmployeeData() {
        return new Object[][]{
                {UserUpdateDtoFactory.withEmptyFirstName()},
                {UserUpdateDtoFactory.withEmptyLastName()},
                {UserUpdateDtoFactory.withInvalidPhoneFormat()},
                {UserUpdateDtoFactory.withWeakPassword()}
        };
    }

    @Issue("FUNC-03")
    @Test(groups = TestGroup.FUNCTIONAL)
    public void shouldUpdateEmployee_whenProfileDataIsValid() {
        UserUpdateRequest employeeToUpdate = UserUpdateDtoFactory.create();

        employeeSteps.update(employeeToUpdate, SC_OK);
        UserCurrentResponse employeeAfterUpdate = employeeSteps.getCurrent(SC_OK);

        assertThat(employeeAfterUpdate.getFirstName()).isEqualTo(employeeToUpdate.getFirstName());
        assertThat(employeeAfterUpdate.getLastName()).isEqualTo(employeeToUpdate.getLastName());
        assertThat(employeeAfterUpdate.getAvatarUrl()).isEqualTo(employeeToUpdate.getAvatarUrl());
        assertThat(employeeAfterUpdate.getPhones()).isEqualTo(employeeToUpdate.getPhones());
    }

    @Issue("FUNC-04")
    @Test(groups = TestGroup.FUNCTIONAL, dataProvider = "invalidEmployeeData")
    public void shouldReturn400_whenProfileDataIsInvalid(UserUpdateRequest invalidData) {
        UserCurrentResponse before = employeeSteps.getCurrent(SC_OK);

        employeeSteps.update(invalidData, SC_BAD_REQUEST);
        UserCurrentResponse after = employeeSteps.getCurrent(SC_OK);

        assertThat(after)
                .usingRecursiveComparison()
                .isEqualTo(before);
    }
}
