package steps;

import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;
import model.dto.EptResponse;
import constants.Routes;

import java.util.List;
import java.util.function.Supplier;

public class EptSteps extends BaseSteps {

    public EptSteps(Supplier<RequestSpecification> spec) {
        super(spec);
    }

    public List<EptResponse> getForCurrentUser(int expectedStatus) {
        return getList(Routes.Ept.CURRENT.getPath(), expectedStatus);
    }
}
