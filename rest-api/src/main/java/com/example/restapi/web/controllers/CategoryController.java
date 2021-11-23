package com.example.restapi.web.controllers;

import com.example.restapi.messages.CategoryMessages;
import com.example.restapi.model.binding.CategoryBindingModel;
import com.example.restapi.model.service.CategoryServiceModel;
import com.example.restapi.model.view.CategoryViewModel;
import com.example.restapi.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> all(){
        List<CategoryServiceModel> categoriesServiceList = categoryService.getAllCategories();

        if(categoriesServiceList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(CategoryMessages.ERROR_GETTING_ALL_CATEGORIES);
        }

        List<CategoryViewModel> categories = categoriesServiceList
                .stream()
                .map(prod -> modelMapper.map(prod, CategoryViewModel.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(categories);
    }

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> create(@Valid @RequestBody CategoryBindingModel categoryBindingModel,
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(bindingResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage));
        }

        if (categoryService.categoryExists(categoryBindingModel.getTitle())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(CategoryMessages.CATEGORY_ALREADY_EXISTS);
        }

        CategoryServiceModel category = this.categoryService.create(modelMapper.map(categoryBindingModel, CategoryServiceModel.class));

        if (category == null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(CategoryMessages.CREATION_NOT_SUCCESSFUL);
        }

        return ResponseEntity.ok(category);
    }
}
