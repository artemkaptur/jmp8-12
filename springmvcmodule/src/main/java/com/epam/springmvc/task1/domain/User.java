package com.epam.springmvc.task1.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class User implements AppEntity {

    private int id;

    @NotNull(message = "Please input your login.")
    private String login;

    @NotNull(message = "Please input your password.")
    @Size(min = 3, max = 20)
    private String password;

    private boolean isAdmin;

    public User() {
    }

    public User(int id, @NotNull(message = "Please input your login.") String login,
                @NotNull(message = "Please input your password.")
                @Size(min = 3, max = 20) String password,
                boolean isAdmin) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                getIsAdmin() == user.getIsAdmin() &&
                getLogin().equals(user.getLogin()) &&
                getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getLogin(), getPassword(), getIsAdmin());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }

}
