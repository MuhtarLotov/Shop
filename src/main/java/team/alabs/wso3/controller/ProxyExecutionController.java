package team.alabs.wso3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import team.alabs.wso3.Constants;
import team.alabs.wso3.model.GrantRoleDto;
import team.alabs.wso3.model.ProxyDto;
import team.alabs.wso3.model.ProxyUpdateDto;
import team.alabs.wso3.service.ProxyExecutionService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProxyExecutionController {

    private final ProxyExecutionService proxyExecutionService;

    @RequestMapping("/proxy/**")
    public ResponseEntity<byte[]> proxy(HttpServletRequest httpServletRequest, @RequestHeader MultiValueMap<String, String> headers) throws IOException {
        return proxyExecutionService.execute(httpServletRequest, headers);
    }

    @PostMapping("/proxy-settings/grant-role")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public Integer grantRole(@Valid @RequestBody GrantRoleDto grantRoleDto) {
        return proxyExecutionService.grantRole(grantRoleDto);
    }

    @GetMapping("/proxy-settings")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public List<ProxyDto> getAll() {
        return proxyExecutionService.getAll();
    }

    @PostMapping("/proxy-settings/batch")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public List<ProxyDto> createProxies(@Valid @RequestBody List<ProxyDto> proxyDtoList) {
        List<ProxyDto> result = new ArrayList<>();
        proxyDtoList.forEach(proxyDto -> result.add(createProxy(proxyDto)));
        return result;
    }

    @PostMapping("/proxy-settings")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public ProxyDto createProxy(@Valid @RequestBody ProxyDto proxyDto) {
        return proxyExecutionService.createProxy(proxyDto);
    }

    @PutMapping("/proxy-settings/{id}")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public ProxyDto updateProxy(@Valid @RequestBody ProxyUpdateDto proxyUpdateDto) {
        return proxyExecutionService.updateProxy(proxyUpdateDto);
    }

    @DeleteMapping("/proxy-settings/{id}")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public Integer delete(@PathVariable Integer id) {
        return proxyExecutionService.delete(id);
    }
}
