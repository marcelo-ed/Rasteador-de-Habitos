package com.marcelo.habit_tracker.ui;

import com.marcelo.habit_tracker.model.Habit;
import com.marcelo.habit_tracker.service.HabitService;

import java.util.List;
import java.util.Scanner;

public class HabitCLI {

    private final HabitService service;
    private final Scanner scanner;

    public HabitCLI(HabitService service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    // Forces the user to press Enter to continue
    private void waitForEnter() {
        System.out.print("\nPressione Enter para continuar...");
        scanner.nextLine();
    }

    // Starts the CLI, resets daily status and shows the main menu
    public void start() {
        service.resetDailyStatus();
        System.out.println("========== Bem-vindo ao Rastreador de Hábitos! ==============");
        showMenu();
    }

    // Main menu loop
    private void showMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\nO que você gostaria de fazer?");
            System.out.println("1. Listar hábitos");
            System.out.println("2. Adicionar hábito");
            System.out.println("3. Concluir hábito");
            System.out.println("4. Deletar hábito");
            System.out.println("5. Sair");
            System.out.print("\nEscolha uma opção: ");

            String input = scanner.nextLine().trim();

            switch (input) {
                case "1" -> listHabits();
                case "2" -> addHabit();
                case "3" -> completeHabit();
                case "4" -> deleteHabit();
                case "5" -> running = false;
                default -> {
                    System.out.println("Opção inválida. Tente novamente.");
                    waitForEnter();
                }
            }
        }

        System.out.println("\nAté logo! Continue mantendo seus hábitos!");
        scanner.close();
    }

    // Displays all habits
    private void listHabits() {
        List<Habit> habits = service.listAllHabits();

        if (habits.isEmpty()) {
            System.out.println("\nNenhum hábito encontrado. Comece adicionando um!");
            waitForEnter();
            return;
        }

        System.out.println("\n--- Seus Hábitos ---");
        for (Habit habit : habits) {
            String status = habit.isConcluded() ? "✔ Concluído" : "X Pendente";
            System.out.printf("• %-20s | Sequência: %d dia(s) | %s%n",
                    habit.getName(), habit.getStreak(), status);
        }

        waitForEnter();
    }

    // Creates a new habit
    private void addHabit() {
        System.out.print("\nNome do hábito: ");
        String name = scanner.nextLine().trim();

        System.out.print("Descrição (opcional, pressione Enter para pular): ");
        String description = scanner.nextLine().trim();
        if (description.isEmpty()) {
            description = null;
        }

        try {
            service.createHabit(name, description);
            System.out.println("Hábito \"" + name + "\" criado com sucesso!");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        waitForEnter();
    }

    // Marks a habit as completed
    private void completeHabit() {
        List<Habit> habits = service.listAllHabits();

        if (habits.isEmpty()) {
            System.out.println("\nNenhum hábito para concluir. Comece adicionando um!");
            waitForEnter();
            return;
        }

        System.out.println("\nQual hábito você concluiu hoje?");
        for (int i = 0; i < habits.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, habits.get(i).getName());
        }
        System.out.print("Escolha um número: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice < 1 || choice > habits.size()) {
                System.out.println("Escolha inválida.");
                waitForEnter();
                return;
            }

            Habit chosen = habits.get(choice - 1);

            if (chosen.isConcluded()) {
                System.out.println("Você já concluiu \"" + chosen.getName() + "\" hoje. Volte amanhã!");
                waitForEnter();
                return;
            }

            Habit habit = service.concludeHabit(chosen.getName());
            System.out.println("Ótimo trabalho! \"" + habit.getName()
                    + "\" concluído. Sequência atual: " + habit.getStreak() + " dia(s).");
        } catch (NumberFormatException e) {
            System.out.println("Por favor, insira um número válido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        waitForEnter();
    }

    // Deletes a habit
    private void deleteHabit() {
        List<Habit> habits = service.listAllHabits();

        if (habits.isEmpty()) {
            System.out.println("\nNenhum hábito para deletar.");
            waitForEnter();
            return;
        }

        System.out.println("\nQual hábito você gostaria de deletar?");
        for (int i = 0; i < habits.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, habits.get(i).getName());
        }
        System.out.print("Escolha um número: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice < 1 || choice > habits.size()) {
                System.out.println("Escolha inválida.");
                waitForEnter();
                return;
            }
            String name = habits.get(choice - 1).getName();
            service.deleteHabit(name);
            System.out.println("Hábito \"" + name + "\" deletado com sucesso.");
        } catch (NumberFormatException e) {
            System.out.println("Por favor, insira um número válido.");
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }

        waitForEnter();
    }
}