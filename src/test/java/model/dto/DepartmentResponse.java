package model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentResponse {
    private int id;
    private String name;
    private int headId;
}
