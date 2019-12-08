package com.epam.springmvc.task1.domain;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.beans.Transient;
import java.util.Arrays;
import java.util.Objects;

public class Product implements AppEntity {

    private int id;

    @NotNull(message = "Name must by at least 1 character.")
    @Size(min = 1, max = 20)
    private String name;

    @NotNull(message = "Please insert file.")
    private byte[] image;

    private String base64;

    private boolean deliveryStatus;

    public Product() {
    }

    public Product(int id, @NotNull(message = "Name must by at least 1 character.") @Size(min = 1, max = 20) String name,
                   @NotNull(message = "Please insert file.") byte[] image, String base64, boolean deliveryStatus) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.base64 = base64;
        this.deliveryStatus = deliveryStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Transient
    public String getBase64() {
        return this.base64 = Base64.encode(this.image);
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public boolean getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(boolean deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return getId() == product.getId() &&
                getDeliveryStatus() == product.getDeliveryStatus() &&
                getName().equals(product.getName()) &&
                Arrays.equals(getImage(), product.getImage()) &&
                getBase64().equals(product.getBase64());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getName(), getBase64(), getDeliveryStatus());
        result = 31 * result + Arrays.hashCode(getImage());
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image=" + Arrays.toString(image) +
                ", base64='" + base64 + '\'' +
                ", deliveryStatus=" + deliveryStatus +
                '}';
    }

}
