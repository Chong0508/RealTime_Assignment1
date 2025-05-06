package org.example;

import java.io.*;
import java.util.Scanner;

public class Assignment1 {
    public static void main(String[] args) {
        File folder = new File("src/Exercise4/src/main/java/org/example");
        int javaFileCount = 0;
        int issuesCount = 0;

        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".java"));

        if(listOfFiles != null) {
            for(File file : listOfFiles) {
                javaFileCount++;
                issuesCount += countSolvedIssues(file);
            }
        }

        System.out.println("Number of Java Files = "+ javaFileCount);
        System.out.println("Number of Issues = "+ issuesCount);
    }

    private static int countSolvedIssues(File file) {
        int count = 0;
        try {
            Scanner scanner = new Scanner(file);
            String line;

            while (scanner.hasNextLine()) {
                line = scanner.nextLine().toUpperCase();
                if(line.contains("// SOLVED")) {
                    count++;
                }
            }

            scanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file.getName());
        }

        return count;
    }
}
