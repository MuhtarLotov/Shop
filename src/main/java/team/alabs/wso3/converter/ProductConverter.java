package team.alabs.wso3.converter;

import org.springframework.stereotype.Component;
import team.alabs.wso3.entity.Ads;
import team.alabs.wso3.entity.Product;
import team.alabs.wso3.model.AdsDto;
import team.alabs.wso3.model.ProductDto;

@Component
public class ProductConverter implements Converter<Product, ProductDto> {



    @Override
    public ProductDto convertToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setProductNameDto(productDto.getProductNameDto());
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
