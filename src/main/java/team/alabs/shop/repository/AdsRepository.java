package team.alabs.shop.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.alabs.shop.entity.Ads;



@Repository
public interface AdsRepository extends JpaRepository<Ads, Integer> {
}
