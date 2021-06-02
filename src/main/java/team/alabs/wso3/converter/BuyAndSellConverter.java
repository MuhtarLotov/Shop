package team.alabs.wso3.converter;

import org.springframework.stereotype.Component;
import team.alabs.wso3.entity.BuyAndSell;
import team.alabs.wso3.model.BuyAndSellDto;

@Component
public class BuyAndSellConverter implements Converter<BuyAndSell, BuyAndSellDto> {


    @Override
    public BuyAndSellDto convertToDto(BuyAndSell buyAndSell) {
        BuyAndSellDto buyAndSellDto = new BuyAndSellDto();
        buyAndSellDto.setId(buyAndSell.getId());
        buyAndSellDto.setPrice(buyAndSell.getPrice());
        buyAndSellDto.setCount(buyAndSell.getCount());
        buyAndSellDto.setBuyUser(buyAndSell.getBuyUser());
        buyAndSellDto.setSellUser(buyAndSell.getSellUser());
        buyAndSellDto.setAds(buyAndSell.getAds());
        return buyAndSellDto;
    }

    @Override

    public BuyAndSell convertToEntity(BuyAndSellDto buyAndSellDto) {
        BuyAndSell buyAndSell = new BuyAndSell();
        if (buyAndSellDto.getId() != null ){
        buyAndSell.setId(buyAndSellDto.getId());}
        buyAndSell.setPrice(buyAndSellDto.getPrice());
        buyAndSell.setCount(buyAndSellDto.getCount());
        buyAndSell.setBuyUser(buyAndSellDto.getBuyUser());
        buyAndSell.setSellUser(buyAndSellDto.getSellUser());
        buyAndSell.setAds(buyAndSellDto.getAds());
        return buyAndSell;
    }
}
