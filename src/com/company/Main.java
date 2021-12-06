package com.company;

import java.util.Collections;
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
        String userOption;
        boolean continueBookLoop = true;
        while (continueBookLoop) {
            System.out.println("Enter 'W' write to the file of book details\nEnter 'R' to read the file with book details\nEnter 'D' to delete files\nEnter 'L' to leave the system");
            while (true) {
                userOption = userInput().toUpperCase();
                if (userOption.equals("W") || userOption.equals("R") || userOption.equals("L") || userOption.equals("D")) {
                    break;
                }
            }
            switch (userOption) {
                case "W":
                    writeToBookFile(true, true);
                    break;
                case "R":
                    readBookFile();
                    break;
                case "D":
                    deleteBookFiles();
                    break;
                case "L":
                    continueBookLoop = false;
                    break;
            }
            System.out.print("\n");
        }
    }

    public static String userInput() {
        Scanner userIn = new Scanner(System.in);
        return userIn.nextLine();
    }

    public static void deleteBookFiles() {
        boolean ISBNFound = false;
        int bookDetailsIndex = 0;
        String[] readableBookDetails = readFile();
        String ISBNToRemove = enterISBN();
        for (int i = 0; i < readableBookDetails.length; i++) {
            if (readableBookDetails[i].equals(ISBNToRemove)) {
                ISBNFound = true;
                bookDetailsIndex = (i - 1);
                break;
            }
        }
        if (ISBNFound) {
            String doubleCheckDelete;
            System.out.println("Book found!\nAre you sure you want to delete " + readableBookDetails[bookDetailsIndex] + " by " + readableBookDetails[bookDetailsIndex + 2] + "?");
            System.out.println("Enter Y/N:");
            while (true) {
                doubleCheckDelete = userInput();
                if (doubleCheckDelete.equalsIgnoreCase("Y") || doubleCheckDelete.equalsIgnoreCase("N")) {
                    break;
                } else {
                    System.out.println("Not a valid input!\nPlease try again\n");
                }
            }
            if (doubleCheckDelete.equalsIgnoreCase("Y")) {
                bookDetails.clear();
                System.out.println(bookDetails);
                Collections.addAll(bookDetails, readableBookDetails);
                ArrayList<String> itemsToRemove = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    itemsToRemove.add(bookDetails.get(bookDetailsIndex + i));
                }
                bookDetails.removeAll(itemsToRemove);
                System.out.println(bookDetails);
                writeToBookFile(true, false);
                System.out.println("Book deleted!");
            }
        }
    }

    public static void readBookFile() {
        String[] readableBookDetails = readFile();
        if (readableBookDetails.length < 4) {
            System.out.println("No books in the system!");
        } else {
            System.out.println("Here are our books:");
            for (int i = 0; i < readableBookDetails.length; i++) {
                System.out.println("Title: " + readableBookDetails[i] + "  ISBN: " + readableBookDetails[i + 1] + "  Author: " + readableBookDetails[i + 2] + "  Genre: " + readableBookDetails[i + 3]);
                i += 3;
            }
        }
    }

    public static String[] readFile() {
        String[] readableBookDetails = new String[0];
        String data = "";
        try {
            Scanner fileReadInput = new Scanner(bookDetailsFile);
            if (fileReadInput.hasNextLine()) {
                data = fileReadInput.nextLine();
            }
            bookDetails.add(data);
            for (String currBookDetail : bookDetails) {
                readableBookDetails = currBookDetail.split(",");
            }
            return readableBookDetails;
        } catch (FileNotFoundException e) {
            System.out.println("Error occurred");
            return new String[]{""};
        }
    }

    public static void writeToBookFile(boolean append, boolean addNewBook) {
        String[] readableBookDetails = readFile();
        try {
            FileWriter myWriter = new FileWriter(bookDetailsFile.getName(),append);
            if (addNewBook) {
                String ISBN;
                boolean bookAlreadyExists = false;
                String title = enterTitle();
                ISBN = enterISBN();
                for (int i = 0; i < readableBookDetails.length; i++) {
                    if (readableBookDetails[i].equals(ISBN)) {
                        System.out.println("A book with this ISBN already exists on the system");
                        bookAlreadyExists = true;
                    }
                }
                if (!bookAlreadyExists) {
                    String author = enterAuthor();
                    String genre = enterGenre();

                    myWriter.write(title + "," + ISBN + "," + author + "," + genre + ",");
                }
            } else {
                for (String currBookDetail : bookDetails) {
                    System.out.println(currBookDetail);
                    myWriter.write(currBookDetail + ",");
                }
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Error occurred");
        }
    }

    public static String enterTitle() {
        String bookTitle;
        System.out.println("Enter the book title:");
        while (true) {
            bookTitle = userInput();
            if (bookTitle.isEmpty()) {
                System.out.println("Inputted value is invalid");
            } else {
                break;
            }
        }
        return bookTitle;
    }

    public static String enterISBN() {
        String ISBN;
        System.out.println("Enter the book ISBN:");
        while (true) {
            ISBN = userInput();
            boolean ISBNCorrect = checkDigit(ISBN);
            if (ISBN.length() != 13 && ISBNCorrect) {
                System.out.println("Inputted value is invalid");
            } else {
                break;
            }
        }
        return ISBN;
    }

    public static boolean checkDigit(String ISBN) {
        int checkDigit = ISBN.charAt(12);
        int total = 0;
        for (int i = 0; i < (ISBN.length() - 1); i++) {
            if (i % 2 == 1) {
                total += Integer.parseInt(String.valueOf(ISBN.charAt(i))) * 3;
            }
            else {
                total += Integer.parseInt(String.valueOf(ISBN.charAt(i)));
            }
        }
        total %= 10;
        if (10 - total == checkDigit) {
            return true;
        }
        else {
            return false;
        }
    }

    public static String enterAuthor() {
        String author;
        System.out.println("Enter the book's author:");
        while (true) {
            author = userInput();
            if (author.isEmpty()) {
                System.out.println("Inputted value is invalid");
            } else {
                break;
            }
        }
        return author;
    }

    public static String enterGenre() {
        String genre;
        System.out.println("Enter the book genre:");
        while (true) {
            genre = userInput();
            if (genre.isEmpty()) {
                System.out.println("Inputted value is invalid");
            } else {
                break;
            }
        }
        return genre;
    }
}
