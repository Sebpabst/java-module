package org.example;

import java.io.Serializable;
import java.util.UUID;

public class Task implements Serializable {
    private final UUID id = UUID.randomUUID();
    private String titre;
    private String description;
    private boolean fait;
    private User user;

    public Task(String titre, String description, User user) {
        this.titre = titre;
        this.description = description;
        this.fait = false;
        this.user = user;
    }

    public UUID getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void isFait(boolean fait) {
        this.fait = fait;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return "[" + (fait ? "X" : " ") + "] Tâche #" + id + " : " + titre + " - " + description + " (Utilisateur : " + user.getFirstName() + ")";
    }
}
