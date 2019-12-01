package com.epam.springmvc.task1.domain;

import java.util.List;
import java.util.Objects;

public class Order implements AppEntity {

    private int id;
    private int userId;
    private List<Purchase> purchases;

    public Order() {
    }

    public Order(int id, int userId, List<Purchase> purchases) {
        this.id = id;
        this.userId = userId;
        this.purchases = purchases;
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

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getId() == order.getId() &&
                getUserId() == order.getUserId() &&
                getPurchases().equals(order.getPurchases());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getPurchases());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", purchases=" + purchases +
                '}';
    }

}
