package model.dto.timelog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeLogCreateRequest {
    private double workTime;
    private String date;
    private String notes;
    private long eptId;
    private String fileUrl;
}
