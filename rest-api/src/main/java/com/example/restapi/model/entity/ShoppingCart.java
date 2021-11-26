package com.example.restapi.model.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "carts")
public class ShoppingCart extends BaseEntity {
    private Set<Product> products;

    public ShoppingCart() {
    }

    @OneToMany
    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
