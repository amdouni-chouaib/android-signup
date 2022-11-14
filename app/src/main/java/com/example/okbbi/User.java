package com.example.okbbi;

public class User {

    private String Email;


    private String Password;


    private String RePassword;

    public User(String email, String password, String rePassword) {
        Email = email;
        Password = password;
        RePassword = rePassword;
    }

    public User() {
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getRePassword() {
        return RePassword;
    }

    public void setRePassword(String rePassword) {
        RePassword = rePassword;
    }
}
