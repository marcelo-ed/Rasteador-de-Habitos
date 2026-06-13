package com.marcelo.habit_tracker.repository;

import com.marcelo.habit_tracker.model.Habit;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HabitRepositoryTest {

    @Test
    void shouldConnectToSupabaseAndReturnHabitList() {
        HabitRepository repository = new HabitRepository();

        List<Habit> habits = repository.findAll();

        assertNotNull(habits, "A lista de hábitos não deve ser nula");
    }

    @Test
    void shouldSaveAndDeleteHabit() {
        HabitRepository repository = new HabitRepository();

        Habit habit = new Habit("Habit de teste", "Criado pelo teste de integração");
        repository.save(habit);

        assertTrue(repository.findByName("Habit de teste").isPresent());

        repository.delete("Habit de teste");

        assertFalse(repository.findByName("Habit de teste").isPresent());
    }
}