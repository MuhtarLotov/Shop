package team.alabs.shop.service;
import team.alabs.shop.entity.Product;
import team.alabs.shop.model.ProductDto;

import java.util.List;

public interface ProductService {

    List<Product> getAllProduct();

    String delete(Integer id);

    ProductDto createProduct(ProductDto productDto);

}
