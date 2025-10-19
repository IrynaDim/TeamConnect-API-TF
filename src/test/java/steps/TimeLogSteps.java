package steps;

import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import model.dto.timelog.TimeLogCreateRequest;
import model.dto.timelog.TimeLogResponse;
import constants.Routes;

import java.util.function.Supplier;

import static constants.Routes.withId;

public class TimeLogSteps extends BaseSteps {

    public TimeLogSteps(Supplier<RequestSpecification> spec) {
        super(spec);
    }

    @Step("Create TimeLog")
    public TimeLogResponse create(TimeLogCreateRequest req, int expectedStatus) {
        return post(Routes.TimeLog.BASE.getPath(), req, expectedStatus, TimeLogResponse.class);
    }

    @Step("Get TimeLog by id {id}")
    public TimeLogResponse getById(long id, int expectedStatus) {
        return get(withId(Routes.TimeLog.BY_ID.getPath(), id), expectedStatus, TimeLogResponse.class);
    }

    @Step("Delete TimeLog by id {id}")
    public void deleteById(long id, int expectedStatus) {
        delete(withId(Routes.TimeLog.BY_ID.getPath(), id), expectedStatus);
    }

    @Step("Silently delete TimeLog by id {id}")
    public void deleteSilently(Long id) {
        deleteSilently(Routes.withId(Routes.TimeLog.BY_ID.getPath(), id));
    }
}
