package steps;

import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import model.dto.NamedIdDto;
import constants.Routes;

import java.util.List;
import java.util.function.Supplier;

public class StackSteps extends BaseSteps {

    public StackSteps(Supplier<RequestSpecification> spec) {
        super(spec);
    }

    public List<NamedIdDto> getAll(int expectedStatus) {
        return getList(Routes.Stack.BASE.getPath(), expectedStatus);
    }
}
