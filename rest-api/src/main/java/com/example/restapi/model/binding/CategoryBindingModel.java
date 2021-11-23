package com.example.restapi.model.binding;

import com.example.restapi.messages.CategoryMessages;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CategoryBindingModel {
    private String title;
    private String imageURL;

    public CategoryBindingModel() {
    }

    @Size(min = 5, max = 30, message = CategoryMessages.TITLE_VALIDATION)
    @NotNull(message = CategoryMessages.TITLE_VALIDATION)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @URL(message = CategoryMessages.IMAGE_URL_VALIDATION)
    @NotNull(message = CategoryMessages.IMAGE_URL_VALIDATION)
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}
