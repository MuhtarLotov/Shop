package team.alabs.shop.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import team.alabs.shop.entity.Ads;
import team.alabs.shop.enums.Status;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BuyAndSellDto {


    private Integer id;
    private Integer count;
    private Double price;
    private UserDto buyUser;
    private UserDto sellUser;
    private Ads ads;
    private Status status;
}
