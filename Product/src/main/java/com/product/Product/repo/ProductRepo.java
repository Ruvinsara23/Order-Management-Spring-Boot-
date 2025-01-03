package com.product.Product.repo;

import com.product.Product.dto.ProductDTO;
import com.product.Product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {
    @Query(value="SELECT * FROM Product WHERE product_id=?1", nativeQuery= true)
    Product getInventoryByItemId(Integer productId);
}
