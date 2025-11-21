package steps;

import io.restassured.specification.RequestSpecification;
import model.dto.NamedIdDto;
import constants.Routes;

import java.util.List;
import java.util.function.Supplier;

public class PositionSteps extends BaseSteps {

    public PositionSteps(Supplier<RequestSpecification> spec) {
        super(spec);
    }

    public List<NamedIdDto> getAll(int expectedStatus) {
        return getList(Routes.Position.BASE.getPath(), expectedStatus);
    }
}
