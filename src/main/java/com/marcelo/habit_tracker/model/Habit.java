package com.marcelo.habit_tracker.model;

import java.time.LocalDate;

public class Habit {
    private String name;
    private String description;
    private int streak;
    private boolean isConcluded;
    private LocalDate createdAt;
    
    

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

	public boolean isConcluded() {
		return isConcluded;
	}

	public void setConcluded(boolean isConcluded) {
		this.isConcluded = isConcluded;
	}
}