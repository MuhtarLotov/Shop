package team.alabs.wso3.model;

import lombok.Data;
import team.alabs.wso3.entity.User;

import javax.validation.constraints.NotEmpty;

@Data
public class RebelDto {
    @NotEmpty
    private Integer id;
    @NotEmpty
    private Integer userId;
    @NotEmpty
    private String breaking;
    private String details;
}
