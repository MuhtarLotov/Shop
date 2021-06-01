package team.alabs.wso3.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team.alabs.wso3.Constants;
import team.alabs.wso3.model.RebelDto;
import team.alabs.wso3.service.impl.RebelServiceImpl;


@RestController
@RequiredArgsConstructor
public class RebelController {

    private final RebelServiceImpl rebelService;

    @PostMapping("/rebel-settings/create/rebel")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public RebelDto createRebel(@RequestBody RebelDto rebelDto) {
        return rebelService.createRebel(rebelDto);
    }


    @PostMapping("/rebel-settings/update/rebel")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public RebelDto updateRebel(@RequestBody RebelDto rebelDto) {
        return rebelService.updateRebel(rebelDto);
    }

}
