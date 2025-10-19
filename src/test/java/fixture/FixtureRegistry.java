package fixture;

import model.dto.user.UserResponse;

import java.util.Map;

public final class FixtureRegistry {

    private FixtureRegistry() {
    }

    public static final Map<Class<?>, String> PATHS = Map.of(
            UserResponse.class, "fixtures/employees/employees.json");
}
