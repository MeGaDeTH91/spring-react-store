package com.example.restapi.model.response.impl;

import com.example.restapi.model.response.UserResponse;

public class UserVerifyResponseModel implements UserResponse {
    private Long id;
    private String username;
    private Boolean isAdministrator;
    private Boolean isActive;

    public UserVerifyResponseModel() {
    }

    public UserVerifyResponseModel(Long id, String username, Boolean isAdministrator, Boolean isActive) {
        this.id = id;
        this.username = username;
        this.isAdministrator = isAdministrator;
        this.isActive = isActive;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAdministrator() {
        return isAdministrator;
    }

    public void setAdministrator(Boolean administrator) {
        isAdministrator = administrator;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}