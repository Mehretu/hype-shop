package com.vvs.hypeshop.service.product;

import com.vvs.hypeshop.model.Product;

import java.util.List;

public interface IProductService {
    Product addProduct(Product product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    void updateProduct(Product product, Long productId);
    List<Product> getAllProducts();

    List<Product> getProductsByCategory(Long categoryId);
}
