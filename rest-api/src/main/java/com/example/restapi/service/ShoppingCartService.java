package com.example.restapi.service;

import com.example.restapi.model.entity.ShoppingCart;
import com.example.restapi.model.service.ShoppingCartServiceModel;

public interface ShoppingCartService {
    ShoppingCartServiceModel getUserCart(Long cartId);

    ShoppingCart create();

    void update(ShoppingCartServiceModel cart);
}
