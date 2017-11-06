package com.example.kevin.vamoae.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by felix on 04/11/2017.
 */

public class EventsResponse {

    @SerializedName("data")
    @Expose
    private List<Events> eventsList;

    @SerializedName("current_page")
    @Expose
    private String page;

    public List<Events> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<Events> eventsList) {
        this.eventsList = eventsList;
    }
}
