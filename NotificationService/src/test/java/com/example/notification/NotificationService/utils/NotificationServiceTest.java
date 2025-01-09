package com.example.notification.NotificationService.utils;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpHeaders;
import java.util.Map;

public class NotificationServiceTest {

    public static void main(String[] args) {
        // Set the URL for the GET request
        String url = "http://localhost:8081/notifications/types"; // Adjust the port if necessary

        // Bearer Token from the logs
        String bearerToken = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiQURNSU4iLCJ1c2VybmFtZSI6ImpvaG5kb2UifQ.KPcARb-sjS8xglmvNvssonmg2BDsiO7D1v1EkRjJ7ME";

        // Create the HTTP client
        HttpClient client = HttpClient.newHttpClient();

        // Create the HTTP request with Authorization and X-Requested-With headers
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer " + bearerToken)
                .build();

        // Send the request and get the response
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Get the response status and body
            int statusCode = response.statusCode();
            String responseBody = response.body();
            HttpHeaders headers = response.headers();

            // Print out the results
            System.out.println("Response Status Code: " + statusCode);
            System.out.println("Response Body: " + responseBody);
            System.out.println("Response Headers: ");
            for (Map.Entry<String, java.util.List<String>> header : headers.map().entrySet()) {
                System.out.println(header.getKey() + ": " + String.join(", ", header.getValue()));
            }

            // Check if the status code is as expected (200 OK)
            if (statusCode == 200) {
                System.out.println("Test Passed");
            } else {
                System.out.println("Test Failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
