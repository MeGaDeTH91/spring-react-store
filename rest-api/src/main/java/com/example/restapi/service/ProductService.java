package com.example.restapi.service;

import com.example.restapi.model.service.ProductServiceModel;

import java.util.List;

public interface ProductService {
    List<ProductServiceModel> getAllProducts();

    boolean productExists(String title);

    ProductServiceModel create(ProductServiceModel productServiceModel);
}