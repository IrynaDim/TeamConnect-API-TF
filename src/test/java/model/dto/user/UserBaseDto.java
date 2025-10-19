package model.dto.user;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;
import model.dto.NamedIdDto;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserBaseDto {
    private Integer id;
    private String firstName;
    private String lastName;

    @JsonAlias({"avatar", "avatarUrl"})
    private String avatarUrl;

    private NamedIdDto position;
}
