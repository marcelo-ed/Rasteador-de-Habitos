package com.marcelo.habit_tracker.service;

import com.marcelo.habit_tracker.model.Habit;
import com.marcelo.habit_tracker.repository.HabitRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class HabitService {

    private final HabitRepository repository;

    public HabitService(HabitRepository repository) {
        this.repository = repository;
    }

    public Habit createHabit(String name, String description) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Habit name must not be empty.");
        }

        if (repository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("A habit with this name already exists: " + name);
        }

        Habit habit = new Habit(name, description);
        repository.save(habit);
        return habit;
    }

    public List<Habit> listAllHabits() {
        return repository.findAll();
    }

    public Habit concludeHabit(String name) {
        Habit habit = findOrThrow(name);

        LocalDate today = LocalDate.now();
        LocalDate lastConclusion = habit.getLastConcludedAt();

        if (lastConclusion != null && lastConclusion.isEqual(today)) {
            // Already concluded today, no changes needed
            return habit;
        }

        if (lastConclusion != null && lastConclusion.isEqual(today.minusDays(1))) {
            // Concluded yesterday: keep the streak going
            habit.setStreak(habit.getStreak() + 1);
        } else {
            // Never concluded, or streak was broken: reset to 1
            habit.setStreak(1);
        }

        habit.setConcluded(true);
        habit.setLastConcludedAt(today);
        repository.save(habit);
        return habit;
    }

    /**
     * Resets the concluded status of all habits.
     * Should be called once per day to allow habits to be concluded again.
     * If a habit was not concluded yesterday, its streak is also reset.
     */
    public void resetDailyStatus() {
        LocalDate yesterday = LocalDate.now().minusDays(1);

        for (Habit habit : repository.findAll()) {
            if (habit.isConcluded()) {
                habit.setConcluded(false);
            } else {
                if (habit.getStreak() > 0) {
                    habit.setStreak(0);
                }
            }

            LocalDate lastConclusion = habit.getLastConcludedAt();
            if (lastConclusion != null && lastConclusion.isBefore(yesterday)) {
                habit.setStreak(0);
            }
            
            
            
            repository.save(habit);
        }
    }

    public void deleteHabit(String name) {
        boolean deleted = repository.delete(name);
        if (!deleted) {
            throw new IllegalArgumentException("Habit not found: " + name);
        }
    }

    public Habit findHabitByName(String name) {
        return findOrThrow(name);
    }

    private Habit findOrThrow(String name) {
        Optional<Habit> habit = repository.findByName(name);
        if (habit.isEmpty()) {
            throw new IllegalArgumentException("Habit not found: " + name);
        }
        return habit.get();
    }
}