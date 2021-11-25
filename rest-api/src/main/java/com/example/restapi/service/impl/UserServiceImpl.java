package com.example.restapi.service.impl;

import com.example.restapi.model.entity.User;
import com.example.restapi.model.service.UserServiceModel;
import com.example.restapi.repository.UserRepository;
import com.example.restapi.service.RoleService;
import com.example.restapi.service.ShoppingCartService;
import com.example.restapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final RoleService roleService;
    private final ShoppingCartService shoppingCartService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(RoleService roleService, ShoppingCartService shoppingCartService, UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.shoppingCartService = shoppingCartService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userEntityOpt = userRepository.findByUsername(username);
        return userEntityOpt.
                map(this::map).
                orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        if (this.userRepository.count() == 0) {
            userServiceModel.setAuthorities(this.roleService.findAll());
        } else {
            userServiceModel.setAuthorities(new LinkedHashSet<>());
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
        }

        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(this.passwordEncoder.encode(userServiceModel.getPassword()));
        user.setCart(shoppingCartService.create());

        return this.modelMapper.map(this.userRepository.saveAndFlush(user), UserServiceModel.class);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public UserServiceModel getById(Long id) {
        return userRepository
                .findById(id)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    @Override
    public UserServiceModel getByUsername(String username) {
        return userRepository
                .findByUsername(username)
                .map(user -> modelMapper.map(user, UserServiceModel.class))
                .orElse(null);
    }

    private UserDetails map(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities());
    }
}
