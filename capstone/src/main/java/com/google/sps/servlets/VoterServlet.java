package com.google.sps.servlets;

import com.google.sps.data.VotingEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.*;

@WebServlet("/VoterServlet")
public class VoterServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    // API Key for Civic API access
    String API_Key = "";
    String electionId = request.getParameter("electionId");
    String address = request.getParameter("address");
    String url = "https://www.googleapis.com/civicinfo/v2/voterinfo";
    String charset = "UTF-8";
    String query =
        String.format(
            "key=%s&address=%s&electionId=%s",
            URLEncoder.encode(API_Key, charset),
            URLEncoder.encode(address, charset),
            URLEncoder.encode(electionId, charset));
    URLConnection connection = new URL(url + "?" + query).openConnection();
    connection.setRequestProperty("Accept-Charset", charset);
    JSONObject electionJsonObject = getJSONObject(connection);
    String electionName = electionJsonObject.get("name").toString();
    String electionDay = electionJsonObject.get("electionDay").toString();
    VotingEvent event = new VotingEvent(electionName, electionDay);
    request.setAttribute("event", event);

    ServletContext servletContext = getServletContext();
    RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/eventlisting");
    requestDispatcher.forward(request, response);
  }

  private JSONObject getJSONObject(URLConnection connection) throws IOException {
    InputStream result = connection.getInputStream();
    Scanner scanner = new Scanner(result);
    JSONObject obj = new JSONObject(scanner.useDelimiter("\\A").next());
    JSONObject electionJsonObject = new JSONObject(obj.get("election").toString());
    return electionJsonObject;
  }
}
