package team.alabs.wso3.service;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import team.alabs.wso3.model.GrantRoleDto;
import team.alabs.wso3.model.ProxyDto;
import team.alabs.wso3.model.ProxyUpdateDto;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface ProxyExecutionService {
    ResponseEntity<byte[]> execute(HttpServletRequest request, MultiValueMap<String, String> headers) throws IOException;

    Integer grantRole(GrantRoleDto grantRoleDto);

    ProxyDto createProxy(ProxyDto proxyDto);

    ProxyDto updateProxy(ProxyUpdateDto proxyUpdateDto);

    List<ProxyDto> getAll();

    Integer delete(Integer id);
}
