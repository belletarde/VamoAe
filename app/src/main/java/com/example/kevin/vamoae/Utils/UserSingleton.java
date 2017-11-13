package com.example.kevin.vamoae.Utils;

/**
 * Created by felix on 12/11/2017.
 */
public class UserSingleton {

    private String token;

    private static final UserSingleton ourInstance = new UserSingleton();

    public static UserSingleton getInstance() {
        return ourInstance;
    }

    private UserSingleton() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}