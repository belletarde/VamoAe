package com.example.kevin.vamoae.model;

import android.provider.ContactsContract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by felix on 04/11/2017.
 */

public class EventsResponse {

    @SerializedName("event")
    @Expose
    private Events eventDetail;

    @SerializedName("data")
    @Expose
    private List<Events> eventsList;

    @SerializedName("last_pgae")
    @Expose
    private int lastPage;

    @SerializedName("current_page")
    @Expose
    private String page;

    public Events getEventDetail() {
        return eventDetail;
    }

    public void setEventDetail(Events eventDetail) {
        this.eventDetail = eventDetail;
    }

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
