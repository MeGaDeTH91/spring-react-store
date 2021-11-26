package com.example.restapi.web.controllers;

import com.example.restapi.messages.UserMessages;
import com.example.restapi.model.binding.UserRegisterBindingModel;
import com.example.restapi.model.binding.UserUpdateBindingModel;
import com.example.restapi.model.service.ShoppingCartServiceModel;
import com.example.restapi.model.service.UserServiceModel;
import com.example.restapi.model.view.UserDetailsViewModel;
import com.example.restapi.service.ShoppingCartService;
import com.example.restapi.service.UserService;
import com.example.restapi.util.JSONResponse;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ShoppingCartService shoppingCartService, ModelMapper modelMapper) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> get(@PathVariable Long id) {
        UserServiceModel userServiceModel = userService.getById(id);
        if (userServiceModel == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(JSONResponse.jsonFromString(UserMessages.USER_DOES_NOT_EXIST));
        }
        return ResponseEntity.ok(modelMapper.map(userServiceModel, UserDetailsViewModel.class));
    }

    @PutMapping(value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Object> update(@PathVariable Long id, @Valid @RequestBody UserUpdateBindingModel userUpdateBindingModel,
                                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(com.example.restapi.util.JSONResponse.jsonFromStream(bindingResult
                            .getFieldErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)));
        }

        UserServiceModel userServiceModel = userService.getById(id);
        if (userServiceModel == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(JSONResponse.jsonFromString(UserMessages.USER_DOES_NOT_EXIST));
        }
        if (!Objects.equals(userServiceModel.getId(), id)) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(JSONResponse.jsonFromString(UserMessages.USER_MISMATCH));
        }
        modelMapper.map(userUpdateBindingModel, userServiceModel);
        UserServiceModel updatedUser = userService.update(userServiceModel);
        return ResponseEntity.ok(updatedUser);
    }

    @PostMapping(value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> register(@Valid @RequestBody UserRegisterBindingModel userRegisterBindingModel,
                                           BindingResult bindingResult) {

        if (bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            ;
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(JSONResponse.jsonFromStream(bindingResult
                            .getFieldErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)));
        }
        UserServiceModel userServiceModel = modelMapper.map(userRegisterBindingModel, UserServiceModel.class);
        if (userService.existsByUsername(userServiceModel.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(JSONResponse.jsonFromString(UserMessages.USERNAME_ALREADY_EXISTS));
        }
        ShoppingCartServiceModel shoppingCart = shoppingCartService.create();
        userServiceModel.setCart(shoppingCart);
        UserServiceModel user = this.userService.register(userServiceModel);

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(JSONResponse.jsonFromString(UserMessages.REGISTRATION_NOT_SUCCESSFUL));
        }

        return ResponseEntity.ok(user);
    }
}
