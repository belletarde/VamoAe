package com.example.kevin.vamoae.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by felix on 15/11/2017.
 */

public class LikeResponse {

    @SerializedName("evento")
    private List<Events> eventsLikeResponse;


    public List<Events> getEventsLikeResponse() {
        return eventsLikeResponse;
    }

    public void setEventsLikeResponse(List<Events> eventsLikeResponse) {
        this.eventsLikeResponse = eventsLikeResponse;
    }
}
