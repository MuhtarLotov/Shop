package team.alabs.shop.service;


import team.alabs.shop.entity.BuyAndSell;
import team.alabs.shop.model.BuyAndSellDto;

import java.util.List;

public interface BuyAndSellService {


    List<BuyAndSell> getAllBuyAndSell();

    String createBuyAndSell(BuyAndSellDto buyAndSellDto);

    void delete(Integer id);

    String buyFromBasket();

    void deleteInBasket();

    List<BuyAndSellDto> getAllBuyAndSellBuyUser();

    List<BuyAndSellDto> getAllBuyAndSellSellUser();
}
