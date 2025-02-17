package org.example;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SAPIClient {

    private String lastPrice = "";
    private String lowestPrice;
    private final String token;
    private final long id;

    public SAPIClient(String token, long id) {
        this.id = id;
        this.token = token;
    }

    public void fetchPrice() {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://steamcommunity.com/market/priceoverview/?currency=UAH&appid=730&market_hash_name=Sticker%20%7C%20One%20Sting"))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject jsonObject = new JSONObject(response.body());
            lowestPrice = jsonObject.getString("lowest_price");
            if (!lowestPrice.equals(lastPrice) && lowestPrice != null) {
                lastPrice = lowestPrice;
                sendMessage();
            }
            System.out.print("lowest price is " + lowestPrice);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage() {
        try {
            String encodedText = URLEncoder.encode("lowest price is " + lowestPrice, StandardCharsets.UTF_8);
            String url = "https://api.telegram.org/bot" + token + "/sendMessage?chat_id=" + id + "&text=" + encodedText;

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
