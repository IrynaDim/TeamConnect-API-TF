package model.dto.timelog;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TimeLogResponse {
    private long id;
    private long eptId;
    private String duration;
    private String note;
    private String payment;
    private String attachment;
    private String date;

    @JsonAlias({"isOverTime", "overTime"})
    private boolean isOverTime;

    @JsonAlias({"isBillable", "billable"})
    private boolean isBillable;
}
