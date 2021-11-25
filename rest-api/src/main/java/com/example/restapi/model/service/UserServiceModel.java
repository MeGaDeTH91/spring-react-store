package com.example.restapi.model.service;

import com.example.restapi.model.entity.ShoppingCart;

import java.util.Set;

public class UserServiceModel extends BaseServiceModel {
    private String username;
    private String password;
    private String email;
    private String address;

    private String firstName;
    private String lastName;

    private ShoppingCart cart;
    private Set<RoleServiceModel> authorities;

    public UserServiceModel() {
    }

    @Override
    public String toString() {
        return getUsername();
    }

    public UserServiceModel(String username, String password, String email, String firstName, String lastName, Set<RoleServiceModel> authorities){
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<RoleServiceModel> getAuthorities() {
        return authorities;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ShoppingCart getCart() {
        return cart;
    }

    public void setCart(ShoppingCart cart) {
        this.cart = cart;
    }

    public void setAuthorities(Set<RoleServiceModel> authorities) {
        this.authorities = authorities;
    }
}
