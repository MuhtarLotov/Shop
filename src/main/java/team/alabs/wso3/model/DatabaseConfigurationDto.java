package team.alabs.wso3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseConfigurationDto {
    private Integer id;
    @NotEmpty
    private String connectionString;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
}
