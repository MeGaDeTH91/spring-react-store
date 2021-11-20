package com.example.restapi.service.impl;

import com.example.restapi.model.binding.UserRegisterBindingModel;
import com.example.restapi.model.entity.User;
import com.example.restapi.model.service.UserServiceModel;
import com.example.restapi.repository.UserRepository;
import com.example.restapi.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userEntityOpt = userRepository.findByUsername(username);
        return userEntityOpt.
                map(this::map).
                orElseThrow(() -> new UsernameNotFoundException("User " + username + " not found!"));
    }

    private UserDetails map(User user) {
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.
                        getAuthorities());
    }

    @Override
    public UserServiceModel registerUser(UserRegisterBindingModel userRegisterBindingModel) {
        return null;
    }
}
