package com.example.restapi.service.impl;

import com.example.restapi.model.entity.ShoppingCart;
import com.example.restapi.model.service.ShoppingCartServiceModel;
import com.example.restapi.repository.ShoppingCartRepository;
import com.example.restapi.service.ShoppingCartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ModelMapper modelMapper;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ModelMapper modelMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ShoppingCartServiceModel getUserCart(Long cartId) {
        Optional<ShoppingCart> cartEntityOpt = shoppingCartRepository.findById(cartId);
        if (cartEntityOpt.isEmpty()) {
            return null;
        }

        return modelMapper.map(cartEntityOpt.get(), ShoppingCartServiceModel.class);
    }

    @Override
    public ShoppingCart create() {
        ShoppingCart cart = new ShoppingCart();
        return shoppingCartRepository.saveAndFlush(cart);
    }

    @Override
    public void update(ShoppingCartServiceModel cart) {
    }
}
