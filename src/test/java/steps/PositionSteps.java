package steps;

import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import model.dto.NamedIdDto;
import constants.Routes;

import java.util.List;
import java.util.function.Supplier;

public class PositionSteps extends BaseSteps {

    public PositionSteps(Supplier<RequestSpecification> spec) {
        super(spec);
    }

    @Step("Get all positions")
    public List<NamedIdDto> getAll(int expectedStatus) {
        return getList(Routes.Position.BASE.getPath(), expectedStatus, NamedIdDto.class);
    }
}
