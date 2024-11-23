package com.product.Product.service;


import com.product.Product.dto.ProductDTO;
import com.product.Product.model.Product;
import com.product.Product.repo.ProductRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {
 @Autowired
 private ProductRepo productRepo;

 @Autowired
 private ModelMapper modelMapper;


 public List<ProductDTO> getAllProduct() {
  List<Product> productsList = productRepo.findAll();
  return modelMapper.map(productsList, new TypeToken<List<ProductDTO>>(){}.getType());
 }

 public ProductDTO saveProduct(ProductDTO productDTO) {
   Product product = modelMapper.map(productDTO, Product.class);
   Product savedProduct =productRepo.save(product);
     return modelMapper.map(savedProduct, ProductDTO.class);
 }
 public ProductDTO updateProduct(ProductDTO productDTO) {
  Product product = modelMapper.map(productDTO, Product.class);
  Product updateProduct = productRepo.save(product);
  return modelMapper.map(updateProduct, ProductDTO.class);
 }

public String deleteProduct(ProductDTO productDTO) {
    Product product = productRepo.findById(productDTO.getId())
            .orElseThrow(() -> new RuntimeException("Product not found"));
    productRepo.delete(product);
    return "Product deleted successfully";
}

}
