package team.alabs.wso3.service;

import team.alabs.wso3.entity.BuyAndSell;
import team.alabs.wso3.model.AdsDto;
import team.alabs.wso3.model.BuyAndSellDto;

import java.util.List;

public interface BuyAndSellService {

    List<BuyAndSell> getAllBuyAndSell();

    void delete(Integer id);

    BuyAndSellDto createBuyAndSell(BuyAndSellDto buyAndSellDto);

}
