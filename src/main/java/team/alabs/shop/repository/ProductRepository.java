package team.alabs.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team.alabs.shop.entity.Product;

import java.util.Optional;


@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {


    @Query("SELECT UPPER(u.productName) FROM Product u WHERE u.productName = :productName")
    Optional<Product> findChekProductName(@Param("productName") String productName);
}
