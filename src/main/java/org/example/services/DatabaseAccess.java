package org.example;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAccess {
    private static DatabaseAccess instance;

    public static DatabaseAccess getInstance() {
        if (instance == null) {
            instance = new DatabaseAccess();
        }
        return instance;
    }

    private DatabaseAccess() {}

    private final List<User> users = new ArrayList<>();
    private final List<Task> tasks = new ArrayList<>();

    public List<User> getUsers() {
        return new ArrayList<>(users); // Retourne une copie pour éviter les modifications externes
    }

    public List<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    public void addUser(User user) {
        if (user != null && findUserById(user.getId()) == null) {
            users.add(user);
        }
    }

    public void addTask(Task task) {
        if (task != null && findTaskById(task.getId()) == null) {
            tasks.add(task);
        }
    }

    public Task findTaskById(int id) {
        for (Task task : tasks) {
            if (task.getId() == id) {
                return task;
            }
        }
        return null;
    }

    public User findUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public boolean deleteTask(int id) {
        Task task = findTaskById(id);
        if (task != null) {
            return tasks.remove(task);
        }
        return false;
    }

    public boolean deleteUser(int id) {
        User user = findUserById(id);
        if (user != null) {
            return users.remove(user);
        }
        return false;
    }

    public boolean updateTask(Task task) {
        if (task == null) return false;

        Task existingTask = findTaskById(task.getId());
        if (existingTask != null) {
            int index = tasks.indexOf(existingTask);
            tasks.set(index, task);
            return true;
        }
        return false;
    }

    public List<Task> getTasksByUser(int userId) {
        List<Task> userTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getUser().getId() == userId) {
                userTasks.add(task);
            }
        }
        return userTasks;
    }
}