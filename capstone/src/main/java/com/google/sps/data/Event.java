package com.google.sps.data;

public class Event{
    String id; 
    String location;
    String summary;

    public void EventSummary(String summary, String id) {
        this.summary = summary;
        this.id = id;
    }

    public void Event(String summary, String id, String location) {
        this.summary = summary;
        this.id = id;
        this.location = location;
    }

    public String getid() {
        return id;
    }

    public String getlocation() {
        return location;
    }

    public String getsummary() {
        return summary;
    } 
