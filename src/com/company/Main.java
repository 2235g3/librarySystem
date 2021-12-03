package com.company;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    private static ArrayList<String> bookDetails = new ArrayList<>();
    private static File bookDetailsFile = new File("bookDetails.csv");

    public static void main(String[] args) {
        String userOption = "";
        boolean continueBookLoop = true;
        while (continueBookLoop) {
            System.out.println("Enter 'W' write to the file of book details\nEnter 'R' to read the file with book details\nEnter 'L' to leave the system");
            while (true) {
                userOption = userInput().toUpperCase();
                if (userOption.equals("W") || userOption.equals("R") || userOption.equals("L")) {
                    break;
                }
            }
            switch (userOption) {
                case "W":
                    writeToBookFile();
                    break;
                case "R":
                    readBookFile();
                    break;
                case "L":
                    continueBookLoop = false;
                    break;
            }
        }
    }
    public static String userInput() {
        Scanner userIn = new Scanner(System.in);
        return userIn.nextLine();
    }
    public static void readBookFile() {
        String[] readableBookDetails = new String[0];
        try {
            Scanner fileReadInput = new Scanner(bookDetailsFile);
            String data = fileReadInput.next();
            bookDetails.add(data);
            for (int i = 0; i < bookDetails.size(); i++) {
                readableBookDetails = bookDetails.get(i).split(",");
            }
            for (int i = 0; i < readableBookDetails.length; i++) {
                System.out.println("Title: " + readableBookDetails[i] + "  ISBN: " + readableBookDetails[i + 1] + "  Author: " + readableBookDetails[i + 2] + "  Genre: " + readableBookDetails[i + 3]);
                i += 3;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred");
        }
    }
    public static void writeToBookFile() {
        try {
            FileWriter myWriter = new FileWriter(bookDetailsFile.getName(), true);
            String title = enterTitle();
            String ISBN = enterISBN();
            String author = enterAuthor();
            String genre = enterGenre();

            myWriter.write(title + "," + ISBN + "," + author + "," + genre + ",");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Error occurred");
        }
    }
    public static String enterTitle() {
        String bookTitle = "";
        System.out.println("Enter the book title:");
        while (true) {
            bookTitle = userInput();
            if (bookTitle.isEmpty()) {
                System.out.println("Inputted value is invalid");
            }
            else {
                break;
            }
        }
        return bookTitle;
    }
    public static String enterISBN() {
        String ISBN = "";
        System.out.println("Enter the book ISBN:");
        while (true) {
            ISBN = userInput();
            if (ISBN.length() != 13) {
                System.out.println("Inputted value is invalid");
            }
            else {
                break;
            }
        }
        return ISBN;
    }
    public static String enterAuthor() {
        String author = "";
        System.out.println("Enter the book's author:");
        while (true) {
            author = userInput();
            if (author.isEmpty()) {
                System.out.println("Inputted value is invalid");
            }
            else {
                break;
            }
        }
        return author;
    }
    public static String enterGenre() {
        String genre = "";
        System.out.println("Enter the book genre:");
        while (true) {
            genre = userInput();
            if (genre.isEmpty()) {
                System.out.println("Inputted value is invalid");
            }
            else {
                break;
            }
        }
        return genre;
    }
}
