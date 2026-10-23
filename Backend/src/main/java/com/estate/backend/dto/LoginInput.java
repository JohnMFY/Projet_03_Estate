package com.estate.backend.dto;

public class LoginInput {

    private String email;
    private String password;

    public LoginInput() {
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}