package team.alabs.shop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team.alabs.shop.converter.ProductConverter;
import team.alabs.shop.entity.Product;
import team.alabs.shop.exception.ValidationException;
import team.alabs.shop.model.ProductDto;
import team.alabs.shop.repository.ProductRepository;
import team.alabs.shop.service.ProductService;
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
        if (productDto.getProductNameDto() != null)
        {
            Product product = productConverter.convertToEntity(productDto);
            productDto = productConverter.convertToDto(product);
            productRepository.save(product);
            return productDto;
        }
        throw new ValidationException("No product name");
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
