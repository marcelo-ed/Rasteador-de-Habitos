package com.marcelo.habit_tracker.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marcelo.habit_tracker.model.Habit;
import io.github.cdimascio.dotenv.Dotenv;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HabitRepository {

    private final String supabaseUrl;
    private final String supabaseKey;
    private final HttpClient client;
    private final ObjectMapper mapper;

    public HabitRepository() {
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();

        String url = dotenv.get("SUPABASE_URL", System.getenv("SUPABASE_URL"));
        String key = dotenv.get("SUPABASE_KEY", System.getenv("SUPABASE_KEY"));

        this.supabaseUrl = url;
        this.supabaseKey = key;
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    // Constructor for testing purposes only
    protected HabitRepository(String supabaseUrl, String supabaseKey) {
        this.supabaseUrl = supabaseUrl;
        this.supabaseKey = supabaseKey;
        this.client = HttpClient.newHttpClient();
        this.mapper = new ObjectMapper();
        this.mapper.registerModule(new JavaTimeModule());
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    // Returns all habits from the database
    public List<Habit> findAll() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl + "/rest/v1/habits?select=*"))
                    .header("apikey", supabaseKey)
                    .header("Authorization", "Bearer " + supabaseKey)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Habit[] habits = mapper.readValue(response.body(), Habit[].class);
            return new ArrayList<>(Arrays.asList(habits));
        } catch (Exception e) {
            System.err.println("Error fetching habits: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Searches for a habit by name, case-insensitive
    public Optional<Habit> findByName(String name) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(supabaseUrl + "/rest/v1/habits?name=ilike." + name.replace(" ", "%20")))
                    .header("apikey", supabaseKey)
                    .header("Authorization", "Bearer " + supabaseKey)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            Habit[] habits = mapper.readValue(response.body(), Habit[].class);
            if (habits.length == 0) {
                return Optional.empty();
                
            }
            return Optional.of(habits[0]);
        } catch (Exception e) {
            System.err.println("Error fetching habit: " + e.getMessage());
            return Optional.empty();
        }
    }

    // Inserts a new habit or updates an existing one
    public void save(Habit habit) {
        try {
            if (habit.getId() == null) {
                // Insert new habit
                String body = mapper.writeValueAsString(habit);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(supabaseUrl + "/rest/v1/habits"))
                        .header("apikey", supabaseKey)
                        .header("Authorization", "Bearer " + supabaseKey)
                        .header("Content-Type", "application/json")
                        .header("Prefer", "return=representation")
                        .POST(HttpRequest.BodyPublishers.ofString(body))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                Habit[] created = mapper.readValue(response.body(), Habit[].class);
                if (created.length > 0) {
                    habit.setId(created[0].getId());
                }
            } else {
                // Update existing habit
                String body = mapper.writeValueAsString(habit);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(supabaseUrl + "/rest/v1/habits?id=eq." + habit.getId()))
                        .header("apikey", supabaseKey)
                        .header("Authorization", "Bearer " + supabaseKey)
                        .header("Content-Type", "application/json")
                        .header("Prefer", "return=representation")
                        .method("PATCH", HttpRequest.BodyPublishers.ofString(body))
                        .build();

                client.send(request, HttpResponse.BodyHandlers.ofString());
            }
        } catch (Exception e) {
            System.err.println("Error saving habit: " + e.getMessage());
        }
    }

    // Deletes a habit by name; returns true if successful
    public boolean delete(String name) {
        Optional<Habit> habit = findByName(name);
        if (habit.isEmpty()) {
            return false;
        }
        try {
            HttpRequest request = HttpRequest.newBuilder()
            			.uri(URI.create(supabaseUrl + "/rest/v1/habits?name=ilike." + name.replace(" ", "%20")))
                    .header("apikey", supabaseKey)
                    .header("Authorization", "Bearer " + supabaseKey)
                    .method("DELETE", HttpRequest.BodyPublishers.noBody())
                    .build();

            client.send(request, HttpResponse.BodyHandlers.ofString());
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting habit: " + e.getMessage());
            return false;
        }
    }
}