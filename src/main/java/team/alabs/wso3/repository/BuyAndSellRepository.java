package team.alabs.wso3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.alabs.wso3.entity.BuyAndSell;

@Repository
public interface BuyAndSellRepository extends JpaRepository<BuyAndSell, Integer> {
}
