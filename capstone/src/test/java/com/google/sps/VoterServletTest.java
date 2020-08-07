package com.google.sps;

import com.google.sps.servlets.VoterServlet;
import java.io.IOException;
import java.net.*;
import java.util.*;
import org.json.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public final class VoterServletTest {
  @Test
  public void rightOutput() throws IOException {
    VoterServlet voting = new VoterServlet();
    String API_key = "";
    String testElectionId = "2000";
    String testAddress = "1000 17th Ave N";
    String charset = "UTF-8";
    String testQuery =
        String.format(
            "key=%s&address=%s&electionId=%s",
            URLEncoder.encode(API_key, charset),
            URLEncoder.encode(testAddress, charset),
            URLEncoder.encode(testElectionId, charset));
    URLConnection testconnection =
        new URL("https://www.googleapis.com/civicinfo/v2/voterinfo" + "?" + testQuery)
            .openConnection();
    JSONObject testelectionJsonObject = new JSONObject();
    testelectionJsonObject.put("ocdDivisionId", "ocd-division/country:us");
    testelectionJsonObject.put("name", "VIP Test Election");
    testelectionJsonObject.put("electionDay", "2021-06-06");
    testelectionJsonObject.put("id", "2000");
    JSONObject voterServletResult = voting.getJSONObject(testconnection);
    String actual = testelectionJsonObject.toString();
    String expected = voterServletResult.toString();
    Assert.assertEquals(expected, actual);
  }
}
