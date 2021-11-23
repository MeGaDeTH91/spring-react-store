package com.example.restapi.service.impl;

import com.example.restapi.model.entity.Category;
import com.example.restapi.model.service.CategoryServiceModel;
import com.example.restapi.repository.CategoryRepository;
import com.example.restapi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoryServiceModel> getAllCategories() {
        return categoryRepository
                .findAll()
                .stream()
                .map(product -> modelMapper.map(product, CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean categoryExists(String title) {
        return categoryRepository.existsByTitle(title);
    }

    @Override
    public CategoryServiceModel create(CategoryServiceModel categoryServiceModel) {
        Category category = this.modelMapper.map(categoryServiceModel, Category.class);

        return this.modelMapper.map(this.categoryRepository.saveAndFlush(category), CategoryServiceModel.class);
    }
}
