package team.alabs.wso3.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.alabs.wso3.entity.Ads;



@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {
}
