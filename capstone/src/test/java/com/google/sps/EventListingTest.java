package com.google.sps;

import com.google.api.services.calendar.model.Event;
import com.google.sps.data.VotingEvent;
import com.google.sps.servlets.EventListing;
import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class EventListingTest {

  @Test
  public void testCreateOneEvent() throws IOException {
    VotingEvent testEvent = new VotingEvent("Test Event", "2020-08-07");
    Event event = EventListing.createEvent(testEvent);
    Assert.assertTrue(
        event.getSummary().contains("Test Event")
            && event.getStart().toString().contains("2020-08-07"));
  }

  @Test
  public void testCreateNullEvent() throws IOException {
    VotingEvent testEvent = new VotingEvent(null, null);
    Assert.assertThrows(
        NullPointerException.class,
        () -> {
          EventListing.createEvent(testEvent);
        });
  }
}
