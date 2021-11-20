package com.example.restapi.service;

import com.example.restapi.model.binding.UserRegisterBindingModel;
import com.example.restapi.model.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserServiceModel registerUser(UserRegisterBindingModel userRegisterBindingModel);

    /*UserServiceModel findByUsername(String username);

    UserServiceModel editUserProfile(UserServiceModel userServiceModel, UserProfileEditBindingModel userProfileEditBindingModel);

    List<UserServiceModel> findAllUsers();

    List<UserProfileViewModel> getAllUsersWithoutRootRole(String username);

    void deleteUserById(String userId);

    void changeUserRole(String userId, String role);

    UserServiceModel findUserById(String id);*/
}
