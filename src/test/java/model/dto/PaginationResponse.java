package model.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse<T> {
    private List<T> data;
    private Pagination pagination;

    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Pagination {

        private Integer totalPages;
        private Integer currentPage;
        private Integer totalEmployees;
    }
}
