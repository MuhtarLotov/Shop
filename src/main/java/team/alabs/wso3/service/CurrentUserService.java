package team.alabs.wso3.service;

import java.util.Set;

public interface CurrentUserService {
    void validateUserIsAdmin();
    void validateUserHasAuthority(Set<String> authorities);
    String getCurrentUserName();
}
