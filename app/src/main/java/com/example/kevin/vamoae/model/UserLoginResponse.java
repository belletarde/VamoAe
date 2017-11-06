package com.example.kevin.vamoae.model;

import java.util.List;

/**
 * Created by felix on 03/11/2017.
 */

public class UserLoginResponse {
    private String success;
    private List<User> user;
    private String error;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
