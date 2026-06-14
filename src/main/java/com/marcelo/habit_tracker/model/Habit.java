package com.marcelo.habit_tracker.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Habit {

	@JsonProperty("id")
	private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("streak")
    private int streak;

    @JsonProperty("is_concluded")
    private boolean isConcluded;

    @JsonProperty("created_at")
    private LocalDate createdAt;

    @JsonProperty("last_concluded_at")
    private LocalDate lastConcludedAt;

    public Habit(String name, String description) {
        this.name = name;
        this.description = description;
        this.streak = 0;
        this.isConcluded = false;
        this.createdAt = LocalDate.now();
    }

    // Required by Jackson for JSON deserialization
    public Habit() {
    }

    // Just getters and setters below
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getLastConcludedAt() {
        return lastConcludedAt;
    }

    public void setLastConcludedAt(LocalDate lastConcludedAt) {
        this.lastConcludedAt = lastConcludedAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    @JsonProperty("is_concluded")
    public boolean isConcluded() {
        return isConcluded;
    }

    public void setConcluded(boolean isConcluded) {
        this.isConcluded = isConcluded;
    }
}