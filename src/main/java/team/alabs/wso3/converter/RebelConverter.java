package team.alabs.wso3.converter;
import org.springframework.stereotype.Service;
import team.alabs.wso3.entity.Rebel;
import team.alabs.wso3.model.RebelDto;


@Service
public class RebelConverter implements Converter<Rebel , RebelDto>{

    @Override

        public RebelDto convertToDto(Rebel rebel) {
            RebelDto rebelDto = new RebelDto();
            rebelDto.setId(rebel.getId());
            rebelDto.setUserId(rebel.getUser().getId());
            rebelDto.setDetails(rebel.getDetails());
            rebelDto.setBreaking(rebel.getBreaking());
            return rebelDto;
    }
    @Override
    public Rebel convertToEntity(RebelDto rebelDto) {
        Rebel rebel = new Rebel();
        rebel.setDetails(rebelDto.getDetails());
        rebel.setBreaking(rebelDto.getBreaking());
        return rebel;
    }
}
