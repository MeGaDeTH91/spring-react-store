package com.example.restapi.service.impl;

import com.example.restapi.model.entity.ShoppingCart;
import com.example.restapi.model.service.ShoppingCartServiceModel;
import com.example.restapi.repository.ShoppingCartRepository;
import com.example.restapi.service.ShoppingCartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ModelMapper modelMapper;

    public ShoppingCartServiceImpl(ShoppingCartRepository shoppingCartRepository, ModelMapper modelMapper) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public ShoppingCartServiceModel create() {
        ShoppingCart cart = new ShoppingCart();
        return modelMapper.map(shoppingCartRepository.saveAndFlush(cart), ShoppingCartServiceModel.class);
    }

    @Override
    public boolean addProduct(Long cartId, Long productId) {
        return false;
    }

    @Override
    public boolean removeProduct(Long cartId, Long productId) {
        return false;
    }

    @Override
    public ShoppingCart get(Long id) {
        return shoppingCartRepository.findById(id).orElse(null);
    }
}
