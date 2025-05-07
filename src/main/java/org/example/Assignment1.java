package org.example;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Assignment1 {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // Testing path: src/main/Exercise4/src/main/java/org/example
        System.out.print("Enter the directory path to check for Java files: ");
        String path = scan.nextLine();
        scan.close();

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".java"));

        if (listOfFiles == null) {
            System.out.println("Invalid directory path or no Java files found!");
            return;
        }

        int javaFileCount = listOfFiles.length;
        AtomicInteger issuesCount = new AtomicInteger(0);
        Thread[] threads = new Thread[javaFileCount];

        for (int i = 0; i < javaFileCount; i++) {
            final File file = listOfFiles[i];

            threads[i] = new Thread(() -> {
                try (Scanner scanner = new Scanner(file)) {
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine().toUpperCase();
                        if (line.contains("// SOLVED")) {
                            issuesCount.incrementAndGet();
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("File not found: " + file.getName());
                }
            });
            threads[i].start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted.");
            }
        }

        System.out.println("Number of Java Files = " + javaFileCount);
        System.out.println("Number of Issues = " + issuesCount.get());
    }
}

