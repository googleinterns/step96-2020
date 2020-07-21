package com.google.sps.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStream;
import java.net.*;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/VoterServlet1")
public class VoterServlet extends HttpServlet {

    @Override
     public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String electionId = request.getParameter("electionId");
        String address = request.getParameter("address");
        String Key_Test = "";
        String key_prod = System.getenv("VARIABLE");
        System.out.println(key_prod);
        String url = "https://www.googleapis.com/civicinfo/v2/voterinfo";
        String charset = "UTF-8";  

        if(Key_Test==null){
            Key_Test = key_prod;
        } 
        String query = String.format("key=%s&address=%s&electionId=%s", 
                        URLEncoder.encode(Key_Test, charset),
                        URLEncoder.encode(address, charset), 
                        URLEncoder.encode(electionId, charset));
        URLConnection connection = new URL(url + "?" + query).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream result = connection.getInputStream();

        try (Scanner scanner = new Scanner(result)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            System.out.println(responseBody);
        }
        

        // Redirect back to the HTML page.
        response.sendRedirect("/event-listing.html");
    }

}
