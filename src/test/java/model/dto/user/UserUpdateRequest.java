package model.dto.user;

import lombok.*;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private Map<String, String> phones;
    private String password;
    private String avatarUrl;
}
