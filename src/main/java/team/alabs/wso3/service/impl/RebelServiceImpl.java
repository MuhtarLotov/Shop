package team.alabs.wso3.service.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.alabs.wso3.converter.RebelConverter;
import team.alabs.wso3.entity.Rebel;
import team.alabs.wso3.model.RebelDto;
import team.alabs.wso3.repository.RebelRepository;
import team.alabs.wso3.repository.UserRepository;


@Service
@RequiredArgsConstructor

public class RebelServiceImpl {

    private final RebelRepository rebelRepository;
    private final RebelConverter rebelConverter;
    private final UserRepository userRepository;


    public RebelDto createRebel(RebelDto rebelDto) {
        Rebel rebel = rebelConverter.convertToEntity(rebelDto);
        rebel.setUser(userRepository.getOne(rebelDto.getUserId()));
        rebel = rebelRepository.save(rebel);
        return rebelConverter.convertToDto(rebel);
    }

    public RebelDto updateRebel(RebelDto rebelDto) {
        Rebel rebel = rebelConverter.convertToEntity(rebelDto);
        rebel.setUser(userRepository.getOne(rebelDto.getUserId()));
        rebel = rebelRepository.save(rebel);
        return rebelConverter.convertToDto(rebel);
    }











}
