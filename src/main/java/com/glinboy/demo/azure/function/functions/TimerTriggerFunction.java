package com.glinboy.demo.azure.function.functions;

import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;

@Component
public class TimerTriggerFunction {

  private static final Logger LOGGER = Logger.getLogger(TimerTriggerFunction.class.getName());

  @FunctionName("callAPIByTimer")
  public void timer(
      @TimerTrigger(name = "timeTrigger", schedule = "0 * * * * *") String timerInfo
  ) throws URISyntaxException, IOException, InterruptedException {
    String url = "https://f415-213-187-82-10.ngrok-free.app/users";

    // Create an HttpClient instance
    HttpClient client = HttpClient.newHttpClient();

    // Build the GET request
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .GET()
        .build();

    // Send the request and get the response
    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    // Log the response status code
    LOGGER.log(Level.INFO, "Status code: {0}", response.statusCode());

    // Log the response body
    LOGGER.log(Level.INFO, "Response body: \n{0}", response.body());
  }
}
