package com.epam.springboot.task1.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Purchase implements AppEntity {

    private int id;
    private int userId;
    private List<Good> goods = new ArrayList<>();

    public Purchase() {
    }

    public Purchase(int id, int userId, List<Good> goods) {
        this.id = id;
        this.userId = userId;
        this.goods = goods;
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

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return getId() == purchase.getId() &&
                getUserId() == purchase.getUserId() &&
                getGoods().equals(purchase.getGoods());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getGoods());
    }

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", userId=" + userId +
                ", goods=" + goods +
                '}';
    }

}
