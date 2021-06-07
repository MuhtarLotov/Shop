package team.alabs.shop.service;

import team.alabs.shop.entity.Ads;
import team.alabs.shop.model.AdsDto;
import java.util.List;

public interface AdsService {

    List<Ads> getAllAds();

    void delete(Integer id);

    AdsDto createAds(AdsDto adsDto);

}
