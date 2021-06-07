package team.alabs.shop.model;

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
    private UserDto user;
    private ProductDto product;
    private Double price;
}
