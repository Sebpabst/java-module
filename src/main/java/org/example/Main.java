package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("What's your name ?");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println("Hello " + name);

        String task2 = new String[]{"test1", "test2", "test3", "test4"};
        for (String task: task2) {
            System.out.println(task);
        }
        String[] tasks3 = new String[task2.length+1];
        System.arraycopy(task2, 0, tasks3, 0, task2.length);
        System.out.println("Ajouter une tache");
        String task = scanner.nextLine();
        tasks3[tasks3.length-1] = task;
        for(String t: tasks3){
            System.out.println(t);
        }
        scanner.close();
    }
}