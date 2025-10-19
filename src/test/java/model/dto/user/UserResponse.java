package model.dto.user;

import lombok.*;
import model.dto.NamedIdDto;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse extends UserBaseDto {
    private String workEmail;
    private String hireDate;
    private String grade;
    private String gender;
    private List<NamedIdDto> technologyStack;
    private NamedIdDto department;
    private List<NamedIdDto> projects;

}
