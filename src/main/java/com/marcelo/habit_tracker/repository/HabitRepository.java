package com.marcelo.habit_tracker.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.marcelo.habit_tracker.model.Habit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class HabitRepository {

    // Path to the JSON file used for persistence
    private static final String FILE_PATH = "habits.json";

    // Jackson's main class for reading and writing JSON
    private final ObjectMapper mapper;

    // In-memory list of habits, loaded from file on startup
    private List<Habit> habits;

    public HabitRepository() {
        this.mapper = new ObjectMapper();

        // Register the JavaTimeModule to handle LocalDate and other java.time types
        this.mapper.registerModule(new JavaTimeModule());

        // Write dates as ISO strings (e.g. "2024-01-15") instead of timestamps
        this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        this.habits = new ArrayList<>();

        // Delegated to a separate method so subclasses can override it (e.g. for testing)
        init();
    }

    // Loads habits from the JSON file; can be overridden by subclasses
    protected void init() {
        this.habits = load();
    }

    // Adds a new habit or replaces an existing one with the same name
    public void save(Habit habit) {
        Optional<Habit> existing = findByName(habit.getName());

        // If a habit with the same name already exists, remove it before re-adding
        if (existing.isPresent()) {
            habits.remove(existing.get());
        }
        habits.add(habit);

        // Write the updated list to the JSON file
        persist();
    }

    // Returns a copy of the full habits list to avoid external modification
    public List<Habit> findAll() {
        return new ArrayList<>(habits);
    }

    // Searches for a habit by name, case-insensitive
    public Optional<Habit> findByName(String name) {
        return habits.stream()
                .filter(h -> h.getName().equalsIgnoreCase(name))
                .findFirst();
    }

    // Removes a habit by name and persists the change; returns true if found and removed
    public boolean delete(String name) {
        boolean removed = habits.removeIf(h -> h.getName().equalsIgnoreCase(name));
        if (removed) {
            persist();
        }
        return removed;
    }

    // Writes the current in-memory list to the JSON file
    private void persist() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), habits);
        } catch (IOException e) {
            System.err.println("Error saving habits: " + e.getMessage());
        }
    }

    // Reads the JSON file and returns the list of habits
    // Returns an empty list if the file does not exist yet
    private List<Habit> load() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try {
            Habit[] array = mapper.readValue(file, Habit[].class);
            return new ArrayList<>(Arrays.asList(array));
        } catch (IOException e) {
            System.err.println("Error loading habits: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}