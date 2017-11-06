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

    @SerializedName("last_pgae")
    @Expose
    private int lastPage;

    @SerializedName("current_page")
    @Expose
    private String page;

    public List<Events> getEventsList() {
        return eventsList;
    }

    public void setEventsList(List<Events> eventsList) {
        this.eventsList = eventsList;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
