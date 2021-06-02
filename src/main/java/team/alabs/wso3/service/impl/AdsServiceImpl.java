package team.alabs.wso3.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.alabs.wso3.converter.AdsConverter;
import team.alabs.wso3.entity.Ads;
import team.alabs.wso3.exception.ValidationException;
import team.alabs.wso3.model.AdsDto;
import team.alabs.wso3.repository.AdsRepository;
import team.alabs.wso3.service.AdsService;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;
    private final AdsConverter adsConverter;


    @Override
    public List<Ads> getAllAds() {
        return adsRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        Optional<Ads> adsOpt = adsRepository.findById(id);
        if (adsOpt.isPresent() && adsOpt.get().isActive()){
            adsOpt.get().setActive(false);
            adsRepository.save(adsOpt.get());
        }
        else
          throw new ValidationException("no ads with this id found: ", id);
    }
    
    @Override
    public AdsDto createAds(AdsDto adsDto) {
        Ads ads = adsConverter.convertToEntity(adsDto);
        adsRepository.save(ads);
        adsDto = adsConverter.convertToDto(ads);
        return adsDto;
    }

}
