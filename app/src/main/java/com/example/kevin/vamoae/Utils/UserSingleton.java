package com.example.kevin.vamoae.Utils;

import com.example.kevin.vamoae.activity.EventLobbyActivity;
import com.example.kevin.vamoae.activity.MainActivity;

import java.util.HashMap;

/**
 * Created by felix on 12/11/2017.
 */
public class UserSingleton {

    private String token;
    private HashMap<String,String> liked = new HashMap<>();

    public HashMap<String,String> getLiked(){
        return liked;
    }

    public void setLiked(String key, String value){
        liked.put(key,value);
    }
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

    public void logout(){
        token = null;
        liked = new HashMap<>();
    }
}