package com.marcelo.habit_tracker;

import com.marcelo.habit_tracker.repository.HabitRepository;
import com.marcelo.habit_tracker.service.HabitService;
import com.marcelo.habit_tracker.ui.HabitCLI;

public class Main {

    public static void main(String[] args) {
        HabitRepository repository = new HabitRepository();
        HabitService service = new HabitService(repository);
        HabitCLI cli = new HabitCLI(service);
        cli.start();
    }
}