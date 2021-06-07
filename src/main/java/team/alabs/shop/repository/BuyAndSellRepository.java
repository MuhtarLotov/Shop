package team.alabs.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team.alabs.shop.entity.BuyAndSell;

import java.util.List;


@Repository
public interface BuyAndSellRepository extends JpaRepository<BuyAndSell, Integer> {

    @Query("SELECT u FROM BuyAndSell u WHERE u.buyUser.id = :idBuyUser AND u.status = 1")
    List<BuyAndSell> getBuyAndSellInBasket(Integer idBuyUser);

    @Query("SELECT u FROM BuyAndSell u WHERE u.buyUser.id = :idBuyUser AND u.status = 0")
    List<BuyAndSell> getAllBuyUser(Integer idBuyUser);

    @Query("SELECT u FROM BuyAndSell u WHERE u.sellUser.id = :idBuyUser AND u.status = 0")
    List<BuyAndSell> getAllSellUser(Integer idBuyUser);

    @Query("SELECT u FROM BuyAndSell u WHERE u.status = 0")
    List<BuyAndSell> getAllBuy();

}
