package com.example.restapi.service.impl;

import com.example.restapi.model.entity.Product;
import com.example.restapi.model.entity.ShoppingCart;
import com.example.restapi.model.service.ShoppingCartServiceModel;
import com.example.restapi.repository.ShoppingCartRepository;
import com.example.restapi.service.ProductService;
import com.example.restapi.service.ShoppingCartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ProductService productService, ModelMapper modelMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }


    @Override
    public ShoppingCartServiceModel create() {
        ShoppingCart cart = new ShoppingCart();
        return modelMapper.map(shoppingCartRepository.saveAndFlush(cart), ShoppingCartServiceModel.class);
    }

    @Override
    public boolean addProduct(Long cartId, Long productId) {
        var cart = shoppingCartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            return false;
        }
        var product = productService.get(productId);
        cart.getProducts().add(modelMapper.map(product, Product.class));

        shoppingCartRepository.save(cart);
        return true;
    }

    @Override
    public boolean removeProduct(Long cartId, Long productId) {
        var cart = shoppingCartRepository.findById(cartId).orElse(null);
        if (cart == null) {
            return false;
        }
        var product = productService.get(productId);
        if (product == null) {
            return false;
        }
        cart.getProducts().removeIf(cartProduct -> Objects.equals(cartProduct.getId(), productId));

        shoppingCartRepository.save(cart);
        return true;
    }

    @Override
    public boolean emptyCart(Long cartId) {
        var cart = shoppingCartRepository.findById(cartId).orElse(null);
        if (cart == null || cart.getProducts() == null) {
            return false;
        }
        for (Product product: cart.getProducts()) {
            productService.reduceQuantity(product.getId());
        }

        cart.getProducts().clear();

        shoppingCartRepository.save(cart);
        return true;
    }

    @Override
    public ShoppingCart get(Long id) {
        return shoppingCartRepository.findById(id).orElse(null);
    }
}
