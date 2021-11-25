package com.example.restapi.service;

import com.example.restapi.model.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserServiceModel register(UserServiceModel userServiceModel);

    boolean existsByUsername(String username);

    boolean existsById(Long id);

    UserServiceModel getById(Long id);

    UserServiceModel getByUsername(String username);

    /*UserServiceModel editUserProfile(UserServiceModel userServiceModel, UserProfileEditBindingModel userProfileEditBindingModel);

    List<UserServiceModel> findAllUsers();

    List<UserProfileViewModel> getAllUsersWithoutRootRole(String username);

    void deleteUserById(String userId);

    void changeUserRole(String userId, String role);

    UserServiceModel findUserById(String id);*/
}
