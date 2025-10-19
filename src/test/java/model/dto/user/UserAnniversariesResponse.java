package model.dto.user;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAnniversariesResponse extends UserBaseDto {
    private String startDate;
    private Integer yearsWorked;
}
