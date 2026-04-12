package com.marcelo.habit_tracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.marcelo.habit_tracker.model.Habit;
import com.marcelo.habit_tracker.repository.HabitRepository;

class HabitServiceTest {

    private static class FakeHabitRepository extends HabitRepository {

        private final List<Habit> store = new ArrayList<>();

        @Override
        protected void init() {
            // Do not load from file during tests
        }

        @Override
        public void save(Habit habit) {
            store.removeIf(h -> h.getName().equalsIgnoreCase(habit.getName()));
            store.add(habit);
        }

        @Override
        public List<Habit> findAll() {
            return new ArrayList<>(store);
        }

        @Override
        public Optional<Habit> findByName(String name) {
            return store.stream()
                    .filter(h -> h.getName().equalsIgnoreCase(name))
                    .findFirst();
        }

        @Override
        public boolean delete(String name) {
            return store.removeIf(h -> h.getName().equalsIgnoreCase(name));
        }
    }

    private HabitService service;

    @BeforeEach
    void setUp() {
        service = new HabitService(new FakeHabitRepository());
    }

    @Test
    void shouldCreateHabitSuccessfully() {
        Habit habit = service.createHabit("Exercise", "30 minutes of exercise");

        assertEquals("Exercise", habit.getName());
        assertEquals(0, habit.getStreak());
        assertFalse(habit.isConcluded());
    }

    @Test
    void shouldThrowWhenCreatingHabitWithBlankName() {
        assertThrows(IllegalArgumentException.class, () -> service.createHabit("  ", "description"));
    }

    @Test
    void shouldThrowWhenCreatingDuplicateHabit() {
        service.createHabit("Exercise", "First");

        assertThrows(IllegalArgumentException.class, () -> service.createHabit("Exercise", "Second"));
    }

    @Test
    void shouldConcludeHabitAndStartStreak() {
        service.createHabit("Exercise", "30 min");

        Habit habit = service.concludeHabit("Exercise");

        assertTrue(habit.isConcluded());
        assertEquals(1, habit.getStreak());
        assertEquals(LocalDate.now(), habit.getLastConcludedAt());
    }

    @Test
    void shouldThrowWhenDeletingNonExistentHabit() {
        assertThrows(IllegalArgumentException.class, () -> service.deleteHabit("Nonexistent"));
    }
}