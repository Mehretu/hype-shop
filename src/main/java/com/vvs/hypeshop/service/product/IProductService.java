package com.vvs.hypeshop.service.product;

import com.vvs.hypeshop.model.Product;
import com.vvs.hypeshop.request.AddProductRequest;
import com.vvs.hypeshop.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest request, Long productId);
    List<Product> getAllProducts();

    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByNameAndBrand(String name, String brand);
    Long countProductsByBrandAndName(String brand, String name);
}
