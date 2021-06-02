package team.alabs.wso3.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team.alabs.wso3.entity.Ads;
import team.alabs.wso3.model.AdsDto;

@Component
@RequiredArgsConstructor
public class AdsConverter implements Converter<Ads, AdsDto> {
    private final UserDtoConverter userDtoConverter;
    private final ProductConverter productConverter;

    @Override
    public AdsDto convertToDto(Ads ads) {
        AdsDto adsDto = new AdsDto();
        adsDto.setId(ads.getId());
        adsDto.setCount(ads.getCount());
        adsDto.setPrice(ads.getPrice());
        adsDto.setUser(userDtoConverter.convertToDto(ads.getUser()));
        adsDto.setProduct(productConverter.convertToDto(ads.getProduct()));
        return adsDto;
    }

    @Override
    public Ads convertToEntity(AdsDto adsDto) {
        Ads ads = new Ads();
        if (ads.getId() != null){
        ads.setId(adsDto.getId());}
        ads.setCount(adsDto.getCount());
        ads.setPrice(adsDto.getPrice());
        ads.setUser(userDtoConverter.convertToEntity(adsDto.getUser()));
        ads.setProduct(productConverter.convertToEntity(adsDto.getProduct()));
        return ads;
    }

}
