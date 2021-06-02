package team.alabs.wso3.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.alabs.wso3.converter.BuyAndSellConverter;
import team.alabs.wso3.entity.Ads;
import team.alabs.wso3.entity.BuyAndSell;
import team.alabs.wso3.exception.ValidationException;
import team.alabs.wso3.model.BuyAndSellDto;
import team.alabs.wso3.repository.AdsRepository;
import team.alabs.wso3.repository.BuyAndSellRepository;
import team.alabs.wso3.service.BuyAndSellService;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BuyAndSellServiceImpl implements BuyAndSellService {
    private final BuyAndSellRepository buyAndSellRepository;
    private final BuyAndSellConverter buyAndSellConverter;
    private final AdsRepository adsRepository;


    @Override
    public List<BuyAndSell> getAllBuyAndSell() {
        return buyAndSellRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        Optional<BuyAndSell> buyAndSellOptional = buyAndSellRepository.findById(id);
        if (buyAndSellOptional.isPresent() && buyAndSellOptional.get().isActive()){
            buyAndSellOptional.get().setActive(false);
            buyAndSellRepository.save(buyAndSellOptional.get());
        }
    }

    @Override
    public BuyAndSellDto createBuyAndSell(BuyAndSellDto buyAndSellDto) {
            BuyAndSell buyAndSell = buyAndSellConverter.convertToEntity(validateAndGet(buyAndSellDto));
            buyAndSellRepository.save(buyAndSell);
            return buyAndSellConverter.convertToDto(buyAndSell);
    }


    public BuyAndSellDto validateAndGet(BuyAndSellDto buyAndSellDto) {
        Optional<Ads> adsOpt = adsRepository.findById(buyAndSellDto.getAds().getId());
        if(adsOpt.isEmpty()){
            throw new ValidationException("");}
        else if (adsOpt.get().getCount() < buyAndSellDto.getCount()){
            throw new ValidationException("not enough quantity of goods %s", adsOpt.get().getId());
        }
        else {
            adsOpt.get().setCount(-buyAndSellDto.getCount());
            adsRepository.save(adsOpt.get());
            return buyAndSellDto;
        }
    }
}
