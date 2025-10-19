package steps;

import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import model.dto.PaginationResponse;
import model.dto.user.*;
import constants.Routes;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static constants.Routes.withId;

public class EmployeeSteps extends BaseSteps {

    public EmployeeSteps(Supplier<RequestSpecification> spec) {
        super(spec);
    }

    @Step("Get employees with anniversaries from {startDate} to {endDate}")
    public List<UserAnniversariesResponse> getWithAnniversary(int expectedStatus, String startDate, String endDate) {
        return getListWithParams(
                Routes.Employee.ANNIVERSARIES.getPath(),
                Map.of("startDate", startDate, "endDate", endDate),
                expectedStatus
        );
    }

    @Step("Get all employees")
    public PaginationResponse<UserBaseDto> getAll(int expectedStatus) {
        return get(Routes.Employee.BASE.getPath(), expectedStatus, PaginationResponse.class);
    }

    @Step("Get current employee info")
    public UserCurrentResponse getCurrent(int expectedStatus) {
        return get(Routes.Employee.CURRENT.getPath(), expectedStatus, UserCurrentResponse.class);
    }

    @Step("Get employee by id {id}")
    public UserResponse getById(long id, int expectedStatus) {
        return get(withId(Routes.Employee.BY_ID.getPath(), id), expectedStatus, UserResponse.class);
    }

    @Step("Update employee profile")
    public void update(UserUpdateRequest dto, int expectedStatus) {
        patch(Routes.Employee.PROFILE.getPath(), dto, expectedStatus);
    }
}
