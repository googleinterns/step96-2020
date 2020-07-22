package com.google.sps.servlets;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/eventlisting")
public class EventListing extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Calendar service = Utils.loadCalendarClient();
    Event event =
        new Event()
            .setSummary("Google I/O 2015")
            .setLocation("800 Howard St., San Francisco, CA 94103")
            .setDescription("A chance to hear more about Google's developer products.");

    DateTime startDateTime = new DateTime("2020-07-22T09:00:00-07:00");
    EventDateTime start =
        new EventDateTime().setDateTime(startDateTime).setTimeZone("America/Los_Angeles");
    event.setStart(start);

    DateTime endDateTime = new DateTime("2020-07-22T17:00:00-07:00");
    EventDateTime end =
        new EventDateTime().setDateTime(endDateTime).setTimeZone("America/Los_Angeles");
    event.setEnd(end);

    EventAttendee[] attendees =
        new EventAttendee[] {
          new EventAttendee().setEmail("sbenfield@google.com"),
        };
    event.setAttendees(Arrays.asList(attendees));

    String calendarId = "primary";
    event = service.events().insert(calendarId, event).execute();
  }
}
