package team.alabs.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.alabs.shop.Constants;
import team.alabs.shop.entity.Ads;
import team.alabs.shop.model.AdsDto;
import team.alabs.shop.service.AdsService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
public class AdsController {

    private final AdsService adsService;

    @GetMapping("/all")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public List<Ads> getAllAds() {return adsService.getAllAds();}

    @PostMapping("/creat")
    public AdsDto createAds(@RequestBody AdsDto adsDto) {
        return adsService.createAds(adsDto);
    }

    @DeleteMapping("/delete")
    public void deleteAds(@RequestParam(value="id") Integer id) {
        adsService.delete(id);
    }
}
