package tests.functional.timelog;

import constants.TestGroup;
import factory.TimeLogDtoFactory;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import model.dto.timelog.TimeLogResponse;
import org.apache.http.HttpStatus;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import steps.TimeLogSteps;
import tests.functional.FunctionalBaseTest;

import java.time.LocalDate;

import static constants.testData.EptTestData.*;
import static org.apache.http.HttpStatus.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Epic("Functional tests")
@Feature("Time Logs")
public class CreateTimeLogFuncTest extends FunctionalBaseTest {

    private final TimeLogSteps timeLogSteps = new TimeLogSteps(this::spec);
    private Long createdTimeLogId;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        createdTimeLogId = null;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (createdTimeLogId != null) {
            timeLogSteps.deleteSilently(createdTimeLogId);
        }
    }

    @Issue("FUNC-04")
    @Test(groups = TestGroup.FUNCTIONAL)
    public void shouldCreateTimeLog_whenDataIsValid() {
        TimeLogResponse created = timeLogSteps.create(
                TimeLogDtoFactory.create(EMPLOYEE_11_DEVELOPMENT), SC_OK);

        createdTimeLogId = created.getId();

        assertThat(created.getId()).isGreaterThan(0);
        assertThat(created.getEptId()).isEqualTo(EMPLOYEE_11_DEVELOPMENT.getId());

        TimeLogResponse retrieved = timeLogSteps.getById(createdTimeLogId, SC_OK);
        assertThat(retrieved).usingRecursiveComparison().isEqualTo(created);

        timeLogSteps.deleteById(createdTimeLogId, SC_OK);
        timeLogSteps.getById(createdTimeLogId, HttpStatus.SC_NOT_FOUND);
    }

    @Issue("FUNC-05")
    @Test(groups = TestGroup.FUNCTIONAL)
    public void shouldReturn400_whenPastWeekIsBeyondGracePeriod() {
        timeLogSteps.create(
                TimeLogDtoFactory.create(EMPLOYEE_11_DEVELOPMENT, LocalDate.now().minusWeeks(2)),
                SC_BAD_REQUEST
        );
    }

    @Issue("FUNC-06")
    @Test(groups = TestGroup.FUNCTIONAL)
    public void shouldReturn400_whenUsingForeignEpt() {
        timeLogSteps.create(
                TimeLogDtoFactory.create(EMPLOYEE_9_DEVELOPMENT),
                SC_BAD_REQUEST
        );
    }
}
