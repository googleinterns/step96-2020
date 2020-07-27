package com.google.sps.servlets;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.*;

@WebServlet("/VoterServlet")
public class VoterServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String electionId = request.getParameter("electionId");
    String address = request.getParameter("address");
    Dotenv dotenv = Dotenv.configure().directory(System.getProperty("user.dir")).load();
    String key_prod = dotenv.get("API_KEY");
    String Key_Test = "";
    String url = "https://www.googleapis.com/civicinfo/v2/voterinfo";
    String charset = "UTF-8";

    if (Key_Test.length() == 0) {
      Key_Test = key_prod;
    }
    String query =
        String.format(
            "key=%s&address=%s&electionId=%s",
            URLEncoder.encode(Key_Test, charset),
            URLEncoder.encode(address, charset),
            URLEncoder.encode(electionId, charset));
    URLConnection connection = new URL(url + "?" + query).openConnection();
    connection.setRequestProperty("Accept-Charset", charset);
    JSONObject electionJsonObject = getJSONObject(connection);
    String electionName = getElectionDay(electionJsonObject);
    String electionDay = getElectionName(electionJsonObject);
    request.setAttribute("name", electionName);
    request.setAttribute("electionDay", electionDay);
    response.sendRedirect("/eventlisting");
  }

  private String getElectionDay(JSONObject electionJsonObject) {
    String name = electionJsonObject.get("name").toString();
    return name;
  }

  private String getElectionName(JSONObject electionJsonObject) {
    String electionDay = electionJsonObject.get("electionDay").toString();
    return electionDay;
  }

  private JSONObject getJSONObject(URLConnection connection) throws IOException {
    InputStream result = connection.getInputStream();
    Scanner scanner = new Scanner(result);
    JSONObject obj = new JSONObject(scanner.useDelimiter("\\A").next());
    return obj;
  }
}