package tests.authz.timeLog;

import annotations.AuthUser;
import constants.TestGroup;
import constants.testData.EptTestData;
import factory.TimeLogDtoFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import model.dto.timelog.TimeLogResponse;
import model.enums.Role;
import org.testng.annotations.Test;
import steps.TimeLogSteps;
import tests.BaseConfiguration;

import static org.apache.http.HttpStatus.*;

@Epic("Authorization")
@Feature("RBAC")
public class TimeLogsDeleteAuthTest extends BaseConfiguration {

    private final TimeLogSteps timeLogSteps = new TimeLogSteps(this::spec);

    @Issue("RBAC-01")
    @Test(groups = TestGroup.RBAC)
    public void shouldDeleteTimeLog_employeeRoleOwnRecord() {
        assertCanDelete(timeLogSteps.create(TimeLogDtoFactory.create(EptTestData.EMPLOYEE_11_DEVELOPMENT), SC_OK), SC_OK);
    }

    @Issue("RBAC-02")
    @Test(groups = TestGroup.RBAC)
    public void shouldReturn400_employeeRoleForeignRecord() {
        timeLogSteps.deleteById(EptTestData.EMPLOYEE_9_DEVELOPMENT.getId(), SC_BAD_REQUEST);
    }

    @Issue("RBAC-03")
    @AuthUser(Role.PM)
    @Test(groups = TestGroup.RBAC)
    public void shouldDeleteTimeLog_pmRoleSubordinateRecord() {
        assertCanDelete(timeLogSteps.create(TimeLogDtoFactory.create(EptTestData.EMPLOYEE_11_DEVELOPMENT), SC_OK), SC_OK);
    }

    @Issue("RBAC-04")
    @AuthUser(Role.PM)
    @Test(groups = TestGroup.RBAC)
    public void shouldReturn400_pmRoleNonSubordinateRecord() {
        timeLogSteps.deleteById(EptTestData.EMPLOYEE_9_DEVELOPMENT.getId(), SC_BAD_REQUEST);
    }

    @Issue("RBAC-05")
    @AuthUser(Role.HR)
    @Test(groups = TestGroup.RBAC)
    public void shouldDeleteTimeLog_hrRoleAnyUser() {
        assertCanDelete(timeLogSteps.create(TimeLogDtoFactory.create(EptTestData.HR_5_INTERVIEW), SC_OK), SC_OK);
    }

    @Issue("RBAC-06")
    @AuthUser(Role.ADMIN)
    @Test(groups = TestGroup.RBAC)
    public void shouldDeleteTimeLog_adminRoleAnyUser() {
        assertCanDelete(timeLogSteps.create(TimeLogDtoFactory.create(EptTestData.HR_5_INTERVIEW), SC_OK), SC_OK);
    }

    private void assertCanDelete(TimeLogResponse timeLog, int expectedStatus) {
        timeLogSteps.deleteById(timeLog.getId(), expectedStatus);
        timeLogSteps.getById(timeLog.getId(), expectedStatus == SC_OK ? SC_NOT_FOUND : SC_OK);
    }
}
