package org.example;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {

    static Zipper zipper = new Zipper();
    static MessageDigestAlgorithm messageDigestAlgorithm = new MessageDigestAlgorithm();
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        menu();
    }

    public static void menu() throws IOException, NoSuchAlgorithmException {
        do {
            System.out.println("""
                    Choose what u want to do:\s
                            1. zip file
                            2. unzip file
                            3. compare md5 of two files
                            """);
            Scanner scanner = new Scanner(System.in);
            String decision = scanner.nextLine();

            switch (decision) {
                case "1" -> {
                    System.out.println("Please enter path to a file u want to zip: ");
                    Scanner scanner2 = new Scanner(System.in);
                    String filename = scanner2.nextLine();
                    File file = new File(filename);
                    zipper.zip(file, filename);
                }
                case "2" -> {
                    System.out.println("Please enter destination dir of a file u want to unzip: ");
                    Scanner scanner3 = new Scanner(System.in);
                    String filepath = scanner3.nextLine();
                    System.out.println("Please enter name of a file u want to unzip: ");
                    String filename2 = scanner3.nextLine();
                    zipper.unzipFile(filepath, filename2);
                }
                case "3" -> {
                    System.out.println("Please enter path to the first file u want to compare: ");
                    Scanner scanner4 = new Scanner(System.in);
                    String filepath1 = scanner4.nextLine();
                    System.out.println("Please enter path to the second file u want to compare: ");
                    String filepath2 = scanner4.nextLine();
                    if (messageDigestAlgorithm.isEqual(filepath1, filepath2)) System.out.println("Files are equal.");
                    else System.out.println("Files ain't equal.");
                }
                case "4" -> {
                    System.out.println("Please enter path to a file u want to zip: ");
                    Scanner scanner2 = new Scanner(System.in);
                    String filename = scanner2.nextLine();
//                    messageDigestAlgorithm.compareMD5(filename);
                }
                default -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
            }
        } while (true);
    }


}
