package team.alabs.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.alabs.shop.converter.BuyAndSellConverter;
import team.alabs.shop.entity.Ads;
import team.alabs.shop.entity.BuyAndSell;
import team.alabs.shop.entity.Product;
import team.alabs.shop.entity.User;
import team.alabs.shop.enums.Status;
import team.alabs.shop.exception.ValidationException;
import team.alabs.shop.model.BuyAndSellDto;
import team.alabs.shop.repository.AdsRepository;
import team.alabs.shop.repository.BuyAndSellRepository;
import team.alabs.shop.repository.UserRepository;
import team.alabs.shop.service.BuyAndSellService;

import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
public class BuyAndSellServiceImpl implements BuyAndSellService {
    private final BuyAndSellRepository buyAndSellRepository;
    private final BuyAndSellConverter buyAndSellConverter;
    private final AdsRepository adsRepository;
    private final CurrentUserServiceImpl currentUserService;
    private final UserRepository userRepository;


    public List<BuyAndSell> getAllBuyAndSell() {
        return buyAndSellRepository.getAllBuy();
    }

    @Override

    public List<BuyAndSellDto> getAllBuyAndSellBuyUser(){
        User user = userRepository.findUserByLogin(currentUserService.getCurrentUserName()).orElseThrow();
        List<BuyAndSell> buyAndSells = buyAndSellRepository.getAllBuyUser(user.getId());
        List<BuyAndSellDto> buyAndSellDto = new ArrayList<>();
        for (BuyAndSell item : buyAndSells ){
            buyAndSellDto.add(buyAndSellConverter.convertToDto(item));
        }
        return buyAndSellDto;
    }
    @Override

    public List<BuyAndSellDto> getAllBuyAndSellSellUser(){
        User user = userRepository.findUserByLogin(currentUserService.getCurrentUserName()).orElseThrow();
        List<BuyAndSell> buyAndSells = buyAndSellRepository.getAllSellUser(user.getId());
        List<BuyAndSellDto> buyAndSellDto = new ArrayList<>();
        for (BuyAndSell item : buyAndSells ){
            buyAndSellDto.add(buyAndSellConverter.convertToDto(item));
        }
        return buyAndSellDto;
    }
    @Override

    public void delete(Integer id) {
        BuyAndSell buyAndSellOpt = buyAndSellRepository.findById(id).orElseThrow();
        if (buyAndSellOpt.getStatus() == Status.BUY) {
            buyAndSellOpt.setActive(false);
            buyAndSellRepository.save(buyAndSellOpt);
        }
    }

    @Transactional
    @Override
    public String createBuyAndSell(BuyAndSellDto buyAndSellDto) {
        BuyAndSellDto buyAndSell = checkAndReserveProduct(buyAndSellDto);
        buyAndSellRepository.save(buyAndSellConverter.convertToEntity(buyAndSell));
        Product product = buyAndSell.getAds().getProduct();
        if (buyAndSellDto.getStatus() == Status.BUY){
            return "Покупка продукта " + product.getProductName();
        }
        return product.getProductName() + " добавле в корзину";
    }

    @Transactional
    @Override
    public String buyFromBasket(){
        List<BuyAndSell> basket = getBasket();
        if ((long) basket.size() == 0){
            return "There is no product in the basket";
        }
        String message = new String();
        for (BuyAndSell item: basket) {
            item.setStatus(Status.BUY);
            buyAndSellRepository.save(item);
            Product product = item.getAds().getProduct();
            message += "Покупка продуктат " + product.getProductName() + " количество " + item.getCount()+"\n";
        }
        return message;
    }

    private BuyAndSellDto checkAndReserveProduct(BuyAndSellDto buyAndSellDto) {
        BuyAndSell buyAndSell = buyAndSellConverter.convertToEntity(buyAndSellDto);
        buyAndSell.setBuyUser(userRepository.findUserByLogin(currentUserService.getCurrentUserName()).orElseThrow());
        Ads adsOpt = adsRepository.findById(buyAndSellDto.getAds().getId()).orElseThrow();
        buyAndSell.setPrice(adsOpt.getPrice());
        buyAndSell.setAds(adsOpt);
        return buyAndSellConverter.convertToDto(validateAndGet(buyAndSell));
    }

    private BuyAndSell validateAndGet(BuyAndSell buyAndSell) {
        Ads ads = adsRepository.findById(buyAndSell.getAds().getId()).orElseThrow();
        if (ads.getCount() < buyAndSell.getCount()) {
            throw new ValidationException("not enough quantity of goods %s", ads.getId());
        } else {
            ads.setCount(ads.getCount() - buyAndSell.getCount());
            adsRepository.save(ads);
            return buyAndSell;
        }
    }

    public void deleteInBasket() {
        List<BuyAndSell> basket = getBasket();
        for (BuyAndSell item : basket) {
            Ads ads = adsRepository.findById(item.getAds().getId()).orElseThrow();
            ads.setCount(ads.getCount() + item.getCount());
            adsRepository.save(ads);
            buyAndSellRepository.delete(item);
        }
    }

    public List<BuyAndSell> getBasket() {
        User user = userRepository.findUserByLogin(currentUserService.getCurrentUserName()).orElseThrow();
        return  buyAndSellRepository.getBuyAndSellInBasket(user.getId());
    }

}
