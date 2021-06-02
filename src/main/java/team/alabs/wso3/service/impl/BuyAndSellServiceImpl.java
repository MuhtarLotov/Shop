package team.alabs.wso3.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.alabs.wso3.converter.BuyAndSellConverter;
import team.alabs.wso3.entity.BuyAndSell;
import team.alabs.wso3.model.BuyAndSellDto;
import team.alabs.wso3.repository.BuyAndSellRepository;
import team.alabs.wso3.service.BuyAndSellService;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class BuyAndSellServiceImpl implements BuyAndSellService {
    private final BuyAndSellRepository buyAndSellRepository;
    private final BuyAndSellConverter buyAndSellConverter;


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
        BuyAndSell buyAndSell = buyAndSellConverter.convertToEntity(buyAndSellDto);
        buyAndSellRepository.save(buyAndSell);
        return buyAndSellConverter.convertToDto(buyAndSell);
    }
}
