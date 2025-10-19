package factory;

import model.dto.user.UserUpdateRequest;

import java.util.Map;

public final class UserUpdateDtoFactory {
    private static final String EMPTY = "";

    private UserUpdateDtoFactory() {
    }

    public static UserUpdateRequest create() {
        return new UserUpdateRequest(
                "Jane",
                "Smith",
                Map.of("home", "+380671112233", "work", "+380631112233"),
                null, // intentionally null
                "https://example.com/avatar/jane-smith.jpg"
        );
    }

    public static UserUpdateRequest withEmptyFirstName() {
        UserUpdateRequest p = copyOf(create());
        p.setFirstName(EMPTY);
        return p;
    }

    public static UserUpdateRequest withEmptyLastName() {
        UserUpdateRequest p = copyOf(create());
        p.setLastName(EMPTY);
        return p;
    }

    public static UserUpdateRequest withInvalidPhoneFormat() {
        UserUpdateRequest p = copyOf(create());
        p.setPhones(Map.of("home", "38067"));
        return p;
    }

    public static UserUpdateRequest withWeakPassword() {
        UserUpdateRequest p = copyOf(create());
        p.setPassword("12345");
        return p;
    }

    private static UserUpdateRequest copyOf(UserUpdateRequest src) {
        return new UserUpdateRequest(
                src.getFirstName(),
                src.getLastName(),
                src.getPhones(),
                null,
                src.getAvatarUrl()
        );
    }
}
