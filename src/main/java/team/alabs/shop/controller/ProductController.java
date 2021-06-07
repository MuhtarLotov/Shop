package team.alabs.shop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.alabs.shop.Constants;
import team.alabs.shop.entity.Product;
import team.alabs.shop.model.ProductDto;
import team.alabs.shop.service.ProductService;
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
    public ProductDto createProduct(@RequestBody ProductDto productDto) {
        return productService.createProduct(productDto);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole(" + Constants.ROLE_ADMIN + ")")
    public String deleteProduct(@RequestParam(value="id") Integer id) {
        return productService.delete(id);
    }
}
