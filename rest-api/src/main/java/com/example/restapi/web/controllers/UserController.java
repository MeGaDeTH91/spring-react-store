package com.example.restapi.web.controllers;

import com.example.restapi.messages.UserMessages;
import com.example.restapi.model.binding.UserRegisterBindingModel;
import com.example.restapi.model.service.UserServiceModel;
import com.example.restapi.service.UserService;
import com.example.restapi.util.JSONResponse;
import org.modelmapper.ModelMapper;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(value = "/register",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> register(@Valid @RequestBody UserRegisterBindingModel userRegisterBindingModel,
                                                     BindingResult bindingResult) {

        if (bindingResult.hasErrors() || !userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {;
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(JSONResponse.jsonFromStream(bindingResult
                            .getFieldErrors()
                            .stream()
                            .map(DefaultMessageSourceResolvable::getDefaultMessage)));
        }
        UserServiceModel userServiceModel = modelMapper.map(userRegisterBindingModel, UserServiceModel.class);
        if (userService.userExists(userServiceModel.getUsername())) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(JSONResponse.jsonFromString(UserMessages.USERNAME_ALREADY_EXISTS));
        }
        UserServiceModel user = this.userService.register(userServiceModel);

        if (user == null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(JSONResponse.jsonFromString(UserMessages.REGISTRATION_NOT_SUCCESSFUL));
        }

        return ResponseEntity.ok(user);
    }
}
