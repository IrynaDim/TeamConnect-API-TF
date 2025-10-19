package model.dto.timelog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DurationDto {
    private int hour;
    private int minute;
    private int second;
    private int nano;
}
