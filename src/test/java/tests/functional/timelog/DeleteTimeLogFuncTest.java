package tests.functional.timelog;

import constants.TestGroup;
import factory.TimeLogDtoFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import model.dto.timelog.TimeLogResponse;
import org.testng.annotations.Test;
import steps.TimeLogSteps;
import tests.functional.FunctionalBaseTest;

import static constants.testData.CommonTestData.NON_EXISTENT_ID;
import static constants.testData.EptTestData.EMPLOYEE_11_DEVELOPMENT;
import static constants.testData.TimeLogTestData.EMPLOYEE_11_OLD_DATE;
import static org.apache.http.HttpStatus.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Epic("Functional tests")
@Feature("Time Logs")
public class DeleteTimeLogFuncTest extends FunctionalBaseTest {

    private final TimeLogSteps timeLogSteps = new TimeLogSteps(this::spec);

    @Issue("FUNC-07")
    @Test(groups = TestGroup.FUNCTIONAL)
    public void shouldDeleteTimeLog_whenCurrentWeek() {
        TimeLogResponse created = timeLogSteps.create(TimeLogDtoFactory.create(EMPLOYEE_11_DEVELOPMENT), SC_OK);
        assertThat(created.getId()).isPositive();

        timeLogSteps.deleteById(created.getId(), SC_OK);
        timeLogSteps.getById(created.getId(), SC_NOT_FOUND);
    }

    @Issue("FUNC-08")
    @Test(groups = TestGroup.FUNCTIONAL)
    public void shouldReturn400_whenDeletingPastWeekTimeLog() {
        timeLogSteps.deleteById(EMPLOYEE_11_OLD_DATE.getId(), SC_BAD_REQUEST);
        timeLogSteps.getById(EMPLOYEE_11_OLD_DATE.getId(), SC_OK);
    }

    @Issue("FUNC-09")
    @Test(groups = TestGroup.FUNCTIONAL)
    public void shouldReturn404_whenDeletingNonExistentTimeLog() {
        timeLogSteps.deleteById(NON_EXISTENT_ID, SC_NOT_FOUND);
    }
}
