package com.example.restapi.model.view;

import com.example.restapi.model.service.BaseServiceModel;

public class CategoryViewModel extends BaseServiceModel {
    private Long id;
    private String title;
    private String imageURL;

    public CategoryViewModel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
