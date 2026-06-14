package com.marcelo.habit_tracker.service;

import com.marcelo.habit_tracker.model.Habit;
import com.marcelo.habit_tracker.repository.HabitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class HabitServiceTest {

    private HabitService service;

    @BeforeEach
    void setUp() {
        service = new HabitService(new HabitRepository());
        // Clean up test habits before each test
        try { service.deleteHabit("Exercise"); } catch (Exception ignored) {}
        try { service.deleteHabit("Reading"); } catch (Exception ignored) {}
    }

    @Test
    void shouldCreateHabitSuccessfully() {
        Habit habit = service.createHabit("Exercise", "30 minutes of exercise");

        assertEquals("Exercise", habit.getName());
        assertEquals(0, habit.getStreak());
        assertFalse(habit.isConcluded());

        service.deleteHabit("Exercise");
    }

    @Test
    void shouldThrowWhenCreatingHabitWithBlankName() {
        assertThrows(IllegalArgumentException.class, () -> service.createHabit("  ", "description"));
    }

    @Test
    void shouldThrowWhenCreatingDuplicateHabit() {
        service.createHabit("Exercise", "First");

        assertThrows(IllegalArgumentException.class, () -> service.createHabit("Exercise", "Second"));

        service.deleteHabit("Exercise");
    }

    @Test
    void shouldConcludeHabitAndStartStreak() {
        service.createHabit("Exercise", "30 min");

        Habit habit = service.concludeHabit("Exercise");

        assertTrue(habit.isConcluded());
        assertEquals(1, habit.getStreak());
        assertEquals(LocalDate.now(), habit.getLastConcludedAt());

        service.deleteHabit("Exercise");
    }

    @Test
    void shouldThrowWhenDeletingNonExistentHabit() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteHabit("Nonexistent"));
    }
}