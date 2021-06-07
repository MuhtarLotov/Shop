package team.alabs.shop.converter;

import org.springframework.stereotype.Component;
import team.alabs.shop.entity.BuyAndSell;
import team.alabs.shop.entity.User;
import team.alabs.shop.model.BuyAndSellDto;


@Component
public class BuyAndSellConverter implements Converter<BuyAndSell, BuyAndSellDto> {

    private final UserDtoConverter userDtoConverter;


    public BuyAndSellConverter(UserDtoConverter userDtoConverter) {
        this.userDtoConverter = userDtoConverter;
    }


    @Override
    public BuyAndSellDto convertToDto(BuyAndSell buyAndSell) {
        BuyAndSellDto buyAndSellDto = new BuyAndSellDto();
        buyAndSellDto.setId(buyAndSell.getId());
        buyAndSellDto.setPrice(buyAndSell.getPrice());
        buyAndSellDto.setCount(buyAndSell.getCount());
        buyAndSellDto.setBuyUser(userDtoConverter.convertToDto(buyAndSell.getBuyUser()));
        buyAndSellDto.setSellUser(userDtoConverter.convertToDto(buyAndSell.getSellUser()));
        buyAndSellDto.setAds(buyAndSell.getAds());
        buyAndSellDto.setStatus(buyAndSell.getStatus());

        return buyAndSellDto;
    }

    @Override
    public BuyAndSell convertToEntity(BuyAndSellDto buyAndSellDto) {
        BuyAndSell buyAndSell = new BuyAndSell();
        if (buyAndSellDto.getId() != null ){
        buyAndSell.setId(buyAndSellDto.getId());}
        buyAndSell.setPrice(buyAndSellDto.getPrice());
        buyAndSell.setCount(buyAndSellDto.getCount());
        buyAndSell.setBuyUser(new User());
        buyAndSell.setSellUser(new User());
        buyAndSell.getBuyUser().setId(buyAndSellDto.getBuyUser().getId());
        buyAndSell.getSellUser().setId(buyAndSellDto.getSellUser().getId());
        buyAndSell.setAds(buyAndSellDto.getAds());
        buyAndSell.setStatus(buyAndSellDto.getStatus());
        return buyAndSell;
    }
}
