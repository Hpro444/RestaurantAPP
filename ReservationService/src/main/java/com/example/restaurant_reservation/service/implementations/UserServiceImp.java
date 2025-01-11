package com.example.restaurant_reservation.service.implementations;

import com.example.restaurant_reservation.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@AllArgsConstructor
public class UserServiceImp implements UserService {

    @Override
    public String getEmailForUser(Long userId) throws IOException, InterruptedException {
        String url = "http://your-server-url/get_email/" + userId;

        HttpResponse<String> response;
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        }

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new RuntimeException("Error: " + response.statusCode());
        }
    }

}
