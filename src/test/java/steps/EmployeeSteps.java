package steps;

import constants.Routes;
import io.restassured.specification.RequestSpecification;
import model.dto.PaginationResponse;
import model.dto.user.*;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static constants.Routes.withId;

public class EmployeeSteps extends BaseSteps {

    public EmployeeSteps(Supplier<RequestSpecification> spec) {
        super(spec);
    }

    public List<UserAnniversariesResponse> getWithAnniversary(int expectedStatus, String startDate, String endDate) {
        return getListWithParams(
                Routes.Employee.ANNIVERSARIES.getPath(),
                Map.of("startDate", startDate, "endDate", endDate),
                expectedStatus
        );
    }

    public PaginationResponse<UserBaseDto> getAll(int expectedStatus) {
        return get(Routes.Employee.BASE.getPath(), expectedStatus, PaginationResponse.class);
    }

    public UserCurrentResponse getCurrent(int expectedStatus) {
        return get(Routes.Employee.CURRENT.getPath(), expectedStatus, UserCurrentResponse.class);
    }

    public UserResponse getById(long id, int expectedStatus) {
        return get(withId(Routes.Employee.BY_ID.getPath(), id), expectedStatus, UserResponse.class);
    }

    public void update(UserUpdateRequest dto, int expectedStatus) {
        patch(Routes.Employee.PROFILE.getPath(), dto, expectedStatus);
    }
}
