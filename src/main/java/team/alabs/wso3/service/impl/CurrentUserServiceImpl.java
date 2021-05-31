package team.alabs.wso3.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import team.alabs.wso3.exception.ForbiddenException;
import team.alabs.wso3.service.CurrentUserService;

import java.util.Collections;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CurrentUserServiceImpl implements CurrentUserService {
    @Override
    public void validateUserIsAdmin() {
        validateUserHasAuthority(Collections.singleton("ADMIN"));
    }

    @Override
    public void validateUserHasAuthority(Set<String> authorities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean notAdmin = authentication.getAuthorities().stream().noneMatch(itm -> authorities.contains(itm.getAuthority()));
        if (notAdmin) {
            throw new ForbiddenException("user has not required authority");
        }
    }

    @Override
    public String getCurrentUserName() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


}
