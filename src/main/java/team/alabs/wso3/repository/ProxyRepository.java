package team.alabs.wso3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team.alabs.wso3.entity.Proxy;
import team.alabs.wso3.entity.User;

import java.util.Optional;

@Repository
public interface ProxyRepository extends JpaRepository<Proxy, Integer> {

    @Query("SELECT p FROM Proxy p WHERE p.service = :service")
    Optional<Proxy> findProxyByService(@Param("service") String service);
}
