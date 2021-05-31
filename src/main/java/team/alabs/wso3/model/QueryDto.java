package team.alabs.wso3.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class QueryDto {
    private Integer id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String sql;
    @NotEmpty
    private Boolean pageable;
    @NotEmpty
    private DataDto data;
}
