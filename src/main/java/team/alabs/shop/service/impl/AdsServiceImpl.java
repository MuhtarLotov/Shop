package team.alabs.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.alabs.shop.converter.AdsConverter;
import team.alabs.shop.entity.Ads;
import team.alabs.shop.exception.ValidationException;
import team.alabs.shop.model.AdsDto;
import team.alabs.shop.repository.AdsRepository;
import team.alabs.shop.repository.UserRepository;
import team.alabs.shop.service.AdsService;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdsServiceImpl implements AdsService {
    private final AdsRepository adsRepository;
    private final AdsConverter adsConverter;
    private final CurrentUserServiceImpl currentUserService;
    private final UserRepository userRepository;



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
          throw new ValidationException("no ads with this id %s found: ", id);
    }
    
    @Override
    public AdsDto createAds(AdsDto adsDto) {
        Ads ads = adsConverter.convertToEntity(adsDto);
        ads.setUser(userRepository.findUserByLogin(currentUserService.getCurrentUserName()).orElseThrow());
        adsRepository.save(ads);
        adsDto = adsConverter.convertToDto(ads);
        return adsDto;
    }

}
