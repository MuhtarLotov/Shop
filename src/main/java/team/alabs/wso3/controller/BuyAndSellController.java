package team.alabs.wso3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.alabs.wso3.Constants;
import team.alabs.wso3.entity.BuyAndSell;
import team.alabs.wso3.model.BuyAndSellDto;
import team.alabs.wso3.service.BuyAndSellService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/buyAndSell")
public class BuyAndSellController {

    private final BuyAndSellService buyAndSellService;

    @GetMapping("/all")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public List<BuyAndSell> getAllBuyAndSell() {return buyAndSellService.getAllBuyAndSell();}

    @PostMapping("/creat")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public BuyAndSellDto createBuyAndSell(@RequestBody BuyAndSellDto buyAndSellDto) {
        return buyAndSellService.createBuyAndSell(buyAndSellDto);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public void deleteBuyAndSell(@RequestParam(value="id") Integer id) {
        buyAndSellService.delete(id);
    }
}
