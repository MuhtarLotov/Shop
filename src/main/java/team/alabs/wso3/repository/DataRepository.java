package team.alabs.wso3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team.alabs.wso3.entity.Data;
import team.alabs.wso3.entity.Proxy;

import java.util.Optional;

@Repository
public interface DataRepository extends JpaRepository<Data, Integer> {

    @Query("SELECT d FROM Data d WHERE d.service = :service")
    Optional<Data> findDataByService(@Param("service") String service);
}
