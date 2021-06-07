package team.alabs.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.alabs.shop.Constants;
import team.alabs.shop.entity.BuyAndSell;
import team.alabs.shop.model.BuyAndSellDto;
import team.alabs.shop.service.BuyAndSellService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buyAndSell")
public class BuyAndSellController {

    private final BuyAndSellService buyAndSellService;

    @GetMapping("/all")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public List<BuyAndSell> getAllBuyAndSell() {return buyAndSellService.getAllBuyAndSell();}


    @GetMapping("/allBuyUser")
    public List<BuyAndSellDto> getAllBuyAndSellBuyUser() {
        return buyAndSellService.getAllBuyAndSellBuyUser();}

    @GetMapping("/allSellUser")
    public List<BuyAndSellDto> getAllBuyAndSellSellUser() {
        return buyAndSellService.getAllBuyAndSellSellUser();}

    @PostMapping("/creat")
    public String createBuyAndSell(@RequestBody BuyAndSellDto buyAndSellDto) {
        return buyAndSellService.createBuyAndSell(buyAndSellDto);
    }

    @DeleteMapping("/delete")
    public void delete(Integer id) {buyAndSellService.delete(id);
    }

    @DeleteMapping("/deleteInBasket")
    public void deleteInBasket() {buyAndSellService.deleteInBasket();}

    @GetMapping("/buyFromBasket")
    public String buyFromBasket() {
        return buyAndSellService.buyFromBasket();
    }








}
