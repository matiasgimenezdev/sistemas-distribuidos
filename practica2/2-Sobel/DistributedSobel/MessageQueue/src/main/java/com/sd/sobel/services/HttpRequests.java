package com.sd.sobel.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

public class HttpRequests {

  public HttpRequests() {}

  public HttpResponse<String> PostHttpRequest(
    String url,
    HashMap<String, String> parametersMap
  ) throws IOException, InterruptedException {
    System.out.println("HTTP REQUEST: " + url + " PARAMS: " + parametersMap);

    HttpResponse<String> response = null;
    var objectMapper = new ObjectMapper();
    String requestBody = objectMapper.writeValueAsString(parametersMap);
    System.out.println("Request body " + requestBody);
    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest
        .newBuilder()
        .uri(URI.create(url))
        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
        .build();

      response = client.send(request, HttpResponse.BodyHandlers.ofString());

      System.out.println(response.body());
    } catch (Exception e) {
      System.err.println("ERROR: " + e.getMessage());
    }

    return response;
  }
}
