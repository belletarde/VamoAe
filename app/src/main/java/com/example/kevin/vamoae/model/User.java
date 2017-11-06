package com.example.kevin.vamoae.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by felix on 03/11/2017.
 */

public class User {
    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String email;
    @SerializedName("api_token")
    @Expose
    private String apiUserToken;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getApiUserToken() {
        return apiUserToken;
    }

    public void setApiUserToken(String apiUserToken) {
        this.apiUserToken = apiUserToken;
    }
}
