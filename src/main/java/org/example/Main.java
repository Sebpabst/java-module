package org.example;

import org.example.models.Task;
import org.example.models.User;
import org.example.services.DatabaseAccess;
import org.example.builders.TaskBuilder;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    private static List<Task> tasks = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int nextTaskId = 1;
    private static User currentUser;

    private static DatabaseAccess databaseAccess = DatabaseAccess.getInstance();

    public static void main(String[] args) {
        System.out.print("Quel est votre prénom ? ");
        String firstName = scanner.nextLine();
        currentUser = new User(firstName);
        databaseAccess.addUser(currentUser);
        System.out.println("Bonjour " + firstName + " !");

        boolean running = true;
        while (running) {
            showMenu();
            int choice = getChoice();

            switch (choice) {
                case 1:
                    listTasks();
                    break;
                case 2:
                    createTask();
                    break;
                case 3:
                    createDatedTask();
                    break;
                case 4:
                    updateTask();
                    break;
                case 5:
                    deleteTask();
                    break;
                case 6:
                    markTaskAsDone();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
        scanner.close();
    }

    private static void showMenu() {
        System.out.println("1. Lister les tâches");
        System.out.println("2. Créer une tâche");
        System.out.println("3. Créer une tâche avec échéance");
        System.out.println("4. Modifier une tâche");
        System.out.println("5. Supprimer une tâche");
        System.out.println("6. Marquer une tâche comme terminée");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
    }

    private static int getChoice() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void listTasks() {
        List<Task> tasks = databaseAccess.getTasks();
        if (tasks.isEmpty()) {
            System.out.println("Aucune tâche trouvée.");
        } else {
            for (Task task : tasks) {
                String status = task.getFait() ? "[TERMINÉE]" : "[EN COURS]";
                System.out.println(status + " " + task);
            }
        }
    }

    private static void createTask() {
        System.out.print("Titre : ");
        String titre = scanner.nextLine();
        System.out.print("Description : ");
        String description = scanner.nextLine();

        Task task = new TaskBuilder()
                .setTitle(titre)
                .setDescription(description)
                .setUser(currentUser)
                .build();

        databaseAccess.addTask(task);
        System.out.println("Tâche créée avec succès !");
    }

    private static void createDatedTask() {
        System.out.print("Titre : ");
        String titre = scanner.nextLine();
        System.out.print("Description : ");
        String description = scanner.nextLine();
        System.out.print("Date d'échéance (format YYYY-MM-DD) : ");
        String dateInput = scanner.nextLine();
    }

    private static void updateTask() {
        listTasks();
        System.out.print("ID de la tâche à modifier : ");
        try {
            UUID id = UUID.fromString(scanner.nextLine());
            Task task = databaseAccess.findTaskById(id);
            if (task != null) {
                System.out.print("Nouveau titre (actuel: " + task.getTitre() + ") : ");
                String newTitre = scanner.nextLine();
                if (!newTitre.trim().isEmpty()) {
                    task.setTitre(newTitre);
                }

                System.out.print("Nouvelle description (actuelle: " + task.getDescription() + ") : ");
                String newDescription = scanner.nextLine();
                if (!newDescription.trim().isEmpty()) {
                    task.setDescription(newDescription);
                }

                databaseAccess.updateTask(task);

                System.out.println("Tâche modifiée avec succès !");
            } else {
                System.out.println("Tâche non trouvée.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID invalide.");
        }
    }

    private static void deleteTask() {
        listTasks();
        System.out.print("ID de la tâche à supprimer : ");
        try {
            UUID id = UUID.fromString(scanner.nextLine());
            if (databaseAccess.deleteTask(id)) {
                tasks.remove(tasks);
                System.out.println("Tâche supprimée avec succès !");
            } else {
                System.out.println("Tâche non trouvée.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID invalide.");
        }
    }

    private static void markTaskAsDone() {
        listTasks();
        System.out.print("ID de la tâche à marquer comme terminée : ");
        try {
            UUID id = UUID.fromString(scanner.nextLine());
            Task task = databaseAccess.findTaskById(id);
            if (task != null) {
                task.isFait(true);
                databaseAccess.updateTask(task);
                System.out.println("Tâche marquée comme terminée !");
            } else {
                System.out.println("Tâche non trouvée.");
            }
        } catch (NumberFormatException e) {
            System.out.println("ID invalide.");
        }
    }

    private static Task findTaskById(UUID id) {
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }
}