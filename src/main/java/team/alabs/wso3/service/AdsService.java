package team.alabs.wso3.service;

import team.alabs.wso3.entity.Ads;
import team.alabs.wso3.entity.Product;
import team.alabs.wso3.model.AdsDto;
import java.util.List;

public interface AdsService {

    List<Ads> getAllAds();

    void delete(Integer id);

    AdsDto createAds(AdsDto adsDto);

}
