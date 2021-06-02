package team.alabs.wso3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdsDto {


    private Integer id;
    private Integer count;
    private Integer userId;
    private Double price;
}
