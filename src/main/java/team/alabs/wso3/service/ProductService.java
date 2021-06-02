package team.alabs.wso3.service;
import team.alabs.wso3.entity.Product;
import team.alabs.wso3.model.ProductDto;

import java.util.List;

public interface ProductService {

    List<Product> getAllProduct();

    String delete(Integer id);

    ProductDto createProduct(ProductDto productDto);

}
