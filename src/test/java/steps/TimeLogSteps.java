package steps;

import constants.Routes;
import io.restassured.specification.RequestSpecification;
import model.dto.timelog.TimeLogCreateRequest;
import model.dto.timelog.TimeLogResponse;

import java.util.function.Supplier;

import static constants.Routes.withId;

public class TimeLogSteps extends BaseSteps {

    public TimeLogSteps(Supplier<RequestSpecification> spec) {
        super(spec);
    }

    public TimeLogResponse create(TimeLogCreateRequest req, int expectedStatus) {
        return post(Routes.TimeLog.BASE.getPath(), req, expectedStatus, TimeLogResponse.class);
    }

    public TimeLogResponse getById(long id, int expectedStatus) {
        return get(withId(Routes.TimeLog.BY_ID.getPath(), id), expectedStatus, TimeLogResponse.class);
    }

    public void deleteById(long id, int expectedStatus) {
        delete(withId(Routes.TimeLog.BY_ID.getPath(), id), expectedStatus);
    }

    public void deleteSilently(Long id) {
        deleteSilently(Routes.withId(Routes.TimeLog.BY_ID.getPath(), id));
    }
}
