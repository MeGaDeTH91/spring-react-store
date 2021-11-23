package com.example.restapi.service.impl;

import com.example.restapi.model.entity.Product;
import com.example.restapi.model.service.ProductServiceModel;
import com.example.restapi.repository.ProductRepository;
import com.example.restapi.service.CategoryService;
import com.example.restapi.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductServiceModel> getAllProducts() {
        return productRepository
                .findAll()
                .stream()
                .map(product -> modelMapper.map(product, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean productExists(String title) {
        return productRepository.existsByTitle(title);
    }

    @Override
    public ProductServiceModel create(ProductServiceModel productServiceModel) {
        Product product = this.modelMapper.map(productServiceModel, Product.class);

        return this.modelMapper.map(this.productRepository.saveAndFlush(product), ProductServiceModel.class);
    }
}
