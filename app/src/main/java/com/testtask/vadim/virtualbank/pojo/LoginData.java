package com.testtask.vadim.virtualbank.pojo;

import java.util.Objects;

public class LoginData {
    private String email;
    private String password;

    public LoginData() {
    }

    public LoginData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoginData)) return false;
        LoginData loginData = (LoginData) o;
        return Objects.equals(email, loginData.email) &&
                Objects.equals(password, loginData.password);
    }

    @Override
    public int hashCode() {

        return Objects.hash(email, password);
    }
}
