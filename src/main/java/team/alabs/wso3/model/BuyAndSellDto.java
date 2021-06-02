package team.alabs.wso3.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.alabs.wso3.entity.Ads;
import team.alabs.wso3.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyAndSellDto {


    private Integer id;
    private Integer count;
    private Double price;
    private User buyUser;
    private User sellUser;
    private Ads ads;
}
