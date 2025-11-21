package steps;

import io.restassured.specification.RequestSpecification;
import model.dto.DepartmentResponse;
import constants.Routes;

import java.util.List;
import java.util.function.Supplier;

public class DepartmentSteps extends BaseSteps {

    public DepartmentSteps(Supplier<RequestSpecification> spec) {
        super(spec);
    }

    public List<DepartmentResponse> getAll(int expectedStatus) {
        return getList(Routes.Department.BASE.getPath(), expectedStatus);
    }
}
