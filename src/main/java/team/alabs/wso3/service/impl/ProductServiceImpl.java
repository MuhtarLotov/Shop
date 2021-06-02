package team.alabs.wso3.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.alabs.wso3.converter.ProductConverter;
import team.alabs.wso3.entity.Product;
import team.alabs.wso3.exception.ValidationException;
import team.alabs.wso3.model.ProductDto;
import team.alabs.wso3.repository.ProductRepository;
import team.alabs.wso3.service.ProductService;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductConverter productConverter;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Product product = productConverter.convertToEntity(productDto);
        productDto = productConverter.convertToDto(product);
        productRepository.save(product);
        return productDto;
    }
    @Override
    public String delete(Integer id) {
        Optional<Product> productOpt = productRepository.findById(id);
       if (productOpt.isPresent() && productOpt.get().isActive()){
           productOpt.get().setActive(false);
           productRepository.save(productOpt.get());
           return productOpt.get().getProductName();
       }
       else
         throw  new ValidationException("No product was found with this id: " + id);
    }
}
