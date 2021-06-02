package team.alabs.wso3.converter;

import org.springframework.stereotype.Component;
import team.alabs.wso3.entity.Ads;
import team.alabs.wso3.model.AdsDto;

@Component
public class AdsConverter implements Converter<Ads, AdsDto> {

    @Override
    public AdsDto convertToDto(Ads ads) {
        AdsDto adsDto = new AdsDto();
        adsDto.setId(ads.getId());
        adsDto.setCount(ads.getCount());
        adsDto.setPrice(ads.getPrice());
        adsDto.setUserId(ads.getUserId());
        return adsDto;
    }

    @Override
    public Ads convertToEntity(AdsDto adsDto) {
        Ads ads = new Ads();
        if (ads.getId() != null){
        ads.setId(adsDto.getId());}
        ads.setCount(adsDto.getCount());
        ads.setPrice(adsDto.getPrice());
        ads.setUserId(adsDto.getUserId());
        return ads;
    }

}
