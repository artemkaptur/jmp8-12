package com.epam.springmvc.task1.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Purchase implements AppEntity {

    private int id;
    private int userId;
    private List<Product> products = new ArrayList<>();

    public Purchase() {
    }

    public Purchase(int id, int userId, List<Product> products) {
        this.id = id;
        this.userId = userId;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return getId() == purchase.getId() &&
                getUserId() == purchase.getUserId() &&
                getProducts().equals(purchase.getProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getProducts());
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", userId=" + userId +
                ", products=" + products +
                '}';
    }

}
