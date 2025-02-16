package org.example;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SAPIClient {
    public void fetchPrice() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://steamcommunity.com/market/priceoverview/?currency=UAH&appid=730&market_hash_name=AK-47%20%7C%20Jaguar%20%28Battle-Scarred%29"))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonObject = new JSONObject(response.body());
            String lowestPrice = jsonObject.getString("lowest_price");
            System.out.print("lowest price is "+lowestPrice);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
