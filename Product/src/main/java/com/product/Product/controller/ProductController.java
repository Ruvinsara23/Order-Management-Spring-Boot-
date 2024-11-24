package com.product.Product.controller;

import com.product.Product.dto.ProductDTO;
import com.product.Product.model.Product;
import com.product.Product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin

@RequestMapping("api/v1")
public class ProductController {

@Autowired
private ProductService productService;


@GetMapping("/getproduct")
    public List<ProductDTO> getProduct() {
    return  productService.getAllProduct();
}

@GetMapping("/getproduct/{productId}")
public ProductDTO getProduct(@PathVariable int productId) {
    return productService.getProductById(productId);

}

@PostMapping("/addproduct")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
    return  productService.saveProduct(productDTO);
}

@PutMapping("/updateproduct")
    public ProductDTO updateProduct(@RequestBody ProductDTO productDTO) {
    return  productService.updateProduct(productDTO);
}

@DeleteMapping("/deleteproduct")
    public String deleteProduct(@RequestBody ProductDTO productDTO) {
    return productService.deleteProduct(productDTO);

}



}
