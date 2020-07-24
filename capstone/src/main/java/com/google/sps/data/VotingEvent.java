package com.google.sps.data;

public class VotingEvent {
  String date;
  String location;
  String name;

  public void VotingEventSummary(String name, String date) {
    this.name = name;
    this.date = date;
  }

  public void VotingEvent(String name, String date, String location) {
    this.name = name;
    this.date = date;
    this.location = location;
  }

  public String getDate() {
    return date;
  }

  public String getLocation() {
    return location;
  }

  public String getName() {
    return name;
  }
}
