package model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.dto.NamedIdDto;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCurrentResponse extends UserBaseDto {
    private String workEmail;
    private String hireDate;
    private String grade;
    private Map<String, String> phones;
    private List<NamedIdDto> technologyStack;
    private NamedIdDto department;
    private String birthDate;
}
