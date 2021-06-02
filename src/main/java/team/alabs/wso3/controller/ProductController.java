package team.alabs.wso3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import team.alabs.wso3.Constants;
import team.alabs.wso3.entity.Product;
import team.alabs.wso3.model.ProductDto;
import team.alabs.wso3.service.ProductService;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public List<Product> getAllRoles() {return productService.getAllProduct();}

    @PostMapping("/creat")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public String deleteProduct(@RequestParam(value="id") Integer id) {
        return productService.delete(id);
    }
}
