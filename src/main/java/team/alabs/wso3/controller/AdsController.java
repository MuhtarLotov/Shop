package team.alabs.wso3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import team.alabs.wso3.Constants;
import team.alabs.wso3.entity.Ads;
import team.alabs.wso3.model.AdsDto;
import team.alabs.wso3.service.AdsService;

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
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public AdsDto createAds(@RequestBody AdsDto adsDto) {
        return adsService.createAds(adsDto);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public void deleteAds(@RequestParam(value="id") Integer id) {
        adsService.delete(id);
    }
}
