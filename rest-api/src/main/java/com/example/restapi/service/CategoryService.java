package com.example.restapi.service;

import com.example.restapi.model.service.CategoryServiceModel;

import java.util.List;

public interface CategoryService {
    List<CategoryServiceModel> getAllCategories();

    boolean categoryExists(String title);

    CategoryServiceModel create(CategoryServiceModel categoryServiceModel);
}
