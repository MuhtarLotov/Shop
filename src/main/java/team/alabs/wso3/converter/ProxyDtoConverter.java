package team.alabs.wso3.converter;

import org.springframework.stereotype.Component;
import team.alabs.wso3.entity.Proxy;
import team.alabs.wso3.exception.ValidationException;
import team.alabs.wso3.model.ProxyDto;

import java.util.HashSet;

@Component
public class ProxyDtoConverter implements Converter<Proxy, ProxyDto> {

    @Override
    public ProxyDto convertToDto(Proxy proxy) {

        ProxyDto proxyDto = new ProxyDto();
        proxyDto.setId(proxy.getId());
        proxyDto.setService(proxy.getService());
        proxyDto.setUrl(proxy.getUrl());
        proxyDto.setRoles(proxy.getRoles());
        return proxyDto;
    }

    @Override
    public Proxy convertToEntity(ProxyDto proxyDto) {

        Proxy proxy = new Proxy();
        proxy.setService(proxyDto.getService());
        proxy.setUrl(proxyDto.getUrl());
        proxy.setRoles(new HashSet<>());
        return proxy;
    }
}
