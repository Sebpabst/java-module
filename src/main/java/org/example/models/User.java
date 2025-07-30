package org.example;

public class User {
    private static int idCompteur = 1;
    private final int id;
    private String firstName;

    public User(String firstName) {
        this.id = idCompteur++;
        this.firstName = firstName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }
}
