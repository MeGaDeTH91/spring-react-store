package com.example.restapi.web.controllers;

import com.example.restapi.messages.ProductMessages;
import com.example.restapi.model.service.ProductServiceModel;
import com.example.restapi.model.view.ProductListViewModel;
import com.example.restapi.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    //@PreAuthorize("hasRole('ADMIN')")
    //@PageTitle("Delete Event")
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
}
