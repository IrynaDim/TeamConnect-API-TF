package model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EptResponse {
    private int id;
    private NamedIdDto project;

    @JsonAlias({"isBillable", "billable"})
    private boolean isBillable;

    private double rate;
    private String startDate;
    private String endDate;
    private TaskDto task;
    private int employeeId;
}
