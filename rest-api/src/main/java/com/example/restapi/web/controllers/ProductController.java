package com.example.restapi.web.controllers;

import com.example.restapi.messages.ProductMessages;
import com.example.restapi.model.binding.ProductBindingModel;
import com.example.restapi.model.service.CategoryServiceModel;
import com.example.restapi.model.service.ProductServiceModel;
import com.example.restapi.model.view.ProductListViewModel;
import com.example.restapi.service.ProductService;
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
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ProductController(ProductService productService, ModelMapper modelMapper) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> all(){
        List<ProductServiceModel> productsServiceList = productService.getAllProducts();

        if(productsServiceList == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ProductMessages.ERROR_GETTING_ALL_PRODUCTS);
        }

        List<ProductListViewModel> products = productsServiceList
                .stream()
                .map(prod -> modelMapper.map(prod, ProductListViewModel.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(products);
    }

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> create(@Valid @RequestBody ProductBindingModel productBindingModel,
                                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(bindingResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage));
        }

        if (productService.productExists(productBindingModel.getTitle())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(ProductMessages.PRODUCT_ALREADY_EXISTS);
        }
        ProductServiceModel productServiceModel = modelMapper.map(productBindingModel, ProductServiceModel.class);
        productServiceModel.setCategory(new CategoryServiceModel(productBindingModel.getCategory()));

        ProductServiceModel product = this.productService.create(productServiceModel);

        if (product == null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(ProductMessages.CREATION_NOT_SUCCESSFUL);
        }

        return ResponseEntity.ok(product);
    }
}
