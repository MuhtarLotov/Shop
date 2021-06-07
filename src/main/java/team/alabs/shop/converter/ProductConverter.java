package team.alabs.shop.converter;

import org.springframework.stereotype.Component;
import team.alabs.shop.entity.Product;
import team.alabs.shop.model.ProductDto;

@Component
public class ProductConverter implements Converter<Product, ProductDto> {



    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setProductNameDto(product.getProductName());
        return productDto;
    }

    @Override
    public Product convertToEntity(ProductDto adsDto) {
        Product product = new Product();
        product.setProductName(adsDto.getProductNameDto());
        if (adsDto.getId() != null){
            product.setId(adsDto.getId());}
        return product;
    }

}
