<%@ page isErrorPage='true' %>
<%@ page contentType="text/html; charset=UTF-8" %>
<h2><%
  out.println("There was an error handling your request."); 
  if (exception.getMessage().contains("Request is missing required authentication credential")) {
    out.print("Please make sure you have logged in first before adding the event to your calendar.");
  }
  if (exception.getMessage().contains("https://www.googleapis.com/civicinfo/v2/voterinfo")) {
    out.print("Please make sure your Election ID exists and you have entered your address correctly.");
  }
%></h2>
