package com.marcelo.habit_tracker.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcelo.habit_tracker.model.Quote;

public class QuoteService {

    private static final String URL = "https://zenquotes.io/api/random";

    private final HttpClient client;
    private final ObjectMapper mapper;

    public QuoteService() {
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
    }

    public Quote getRandomQuote() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            Quote[] quotes = mapper.readValue(response.body(), Quote[].class);

            return quotes[0];

        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar quote da API", e);
        }
    }
}