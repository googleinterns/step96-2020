package net.javaguides.servlet.tutorial.examples;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/VoterServlet")
public class VoterServlet extends HttpServlet {

    @Override
     public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String inputID = request.getParameter("inputID");
        String address = request.getParameter("address");
        // Redirect back to the HTML page.
        response.sendRedirect("/event-listing.html");
    }

}
