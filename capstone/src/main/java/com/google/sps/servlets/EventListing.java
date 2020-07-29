package com.google.sps.servlets;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.sps.data.VotingEvent;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/eventlisting")
public class EventListing extends HttpServlet {
  VotingEvent votingEvent;

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    votingEvent = (VotingEvent) request.getAttribute("event");
    request.setAttribute("name", votingEvent.getName());
    request.setAttribute("date", votingEvent.getDate());

    RequestDispatcher requestDispatcher = request.getRequestDispatcher("eventDisplay.jsp");
    requestDispatcher.forward(request, response);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    if (!votingEvent == null) {
      insertEvent(createEvent(votingEvent));
    }
    response.sendRedirect("/eventlisting.html");
  }

  public Event createEvent(VotingEvent votingEvent) throws IOException {
    Event event = new Event().setSummary(votingEvent.getName());
    event.setStart(new EventDateTime().setDate(new DateTime(votingEvent.getDate())));
    event.setEnd(new EventDateTime().setDate(new DateTime(votingEvent.getDate())));
    return event;
  }

  public void insertEvent(Event event) throws IOException {
    Calendar service = Utils.loadCalendarClient();
    String calendarId = "primary";
    event = service.events().insert(calendarId, event).execute();
  }
}
