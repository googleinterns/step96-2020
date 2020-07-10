package com.google.sps.servlets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import com.google.api.services.calendar.model.EventAttendee;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.security.GeneralSecurityException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.io.BufferedReader;

@WebServlet("/eventlisting")
public class EventListing extends HttpServlet {	
	HttpTransport httpTransport;
	JacksonFactory jsonFactory;
	Credential credential;
	
	@Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        try {
		EventListing calendarHandler = new EventListing();
        calendarHandler.createEvents(calendarHandler.setUp(response));
        } catch (GeneralSecurityException e) {}
	}
	
	public Calendar setUp(HttpServletResponse response) throws GeneralSecurityException, IOException {		
		httpTransport = GoogleNetHttpTransport.newTrustedTransport();
	    jsonFactory = JacksonFactory.getDefaultInstance();

        String APPLICATION_NAME = "Voting Calendar Event Listing";
	    String clientId = "394813145192-gjkm8oi4mudp4mra7s927btgis3oduj1.apps.googleusercontent.com";
	    String clientSecret = "5S4Qks50hBx0MNH-ycLKJdZj";
	    String redirectUrl = "https://8080-b1745f48-dfde-48a9-bee2-9b67700361a7.us-east1.cloudshell.dev/event-listing.html";
	    String scope = "https://www.googleapis.com/auth/calendar";

	    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow(
	        httpTransport, jsonFactory, clientId, clientSecret, Collections.singleton(scope));
	    
	    // Authorize
	    String authorizationUrl = flow.newAuthorizationUrl().setRedirectUri(redirectUrl).build();

	    // Point or redirect your user to the authorizationUrl.
	    System.out.println("Go to the following link in your browser:");
	    System.out.println(authorizationUrl);

	    // Read the authorization code from the standard input stream.
	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	    String code = in.readLine();

	    // Exchange
	    GoogleTokenResponse responses = flow.newTokenRequest(code).setRedirectUri(redirectUrl)
	        .execute();
	    
	    credential = new GoogleCredential.Builder()
	        .setTransport(httpTransport)
	        .setJsonFactory(jsonFactory)
	        .setClientSecrets(clientId, clientSecret)
	        .build().setFromTokenResponse(responses);

        return new Calendar.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
	}

    public void createEvents(Calendar service) throws IOException{
        Event event = new Event()
            .setSummary("Google I/O 2015")
            .setLocation("800 Howard St., San Francisco, CA 94103")
            .setDescription("A chance to hear more about Google's developer products.");

        DateTime startDateTime = new DateTime("2020-07-208T09:00:00-07:00");
        EventDateTime start = new EventDateTime()
            .setDateTime(startDateTime)
            .setTimeZone("America/Los_Angeles");
        event.setStart(start);

        DateTime endDateTime = new DateTime("2020-07-208T17:00:00-07:00");
        EventDateTime end = new EventDateTime()
            .setDateTime(endDateTime)
            .setTimeZone("America/Los_Angeles");
        event.setEnd(end);

        EventAttendee[] attendees = new EventAttendee[] {
            new EventAttendee().setEmail("sbenfield@google.com"),
        };
        event.setAttendees(Arrays.asList(attendees));

        String calendarId = "primary";
        event = service.events().insert(calendarId, event).execute();
        System.out.printf("Event created: %s\n", event.getHtmlLink());
    }
}
 