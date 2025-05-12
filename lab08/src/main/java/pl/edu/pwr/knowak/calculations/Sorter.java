package pl.edu.pwr.knowak.calculations;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Sorter {

    public Double[] a;
    public Double[] b;
    public Boolean order;

    static {
        System.loadLibrary("native");
    }
    public static void main(String[] args) {

        while(true) {
            System.out.println("""
                    Choose method:
                    1. sort01
                    2. sort02
                    3. sort03
                    4. sort04""");

            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine();

            Double[] array = new Double[10];
            System.out.println("Generated array: ");
            for (int i = 0; i < array.length; i++) {
                array[i] = Math.random() * 100;
                System.out.print(array[i] + " ");
            }
            System.out.println();
            int order = 0;
            switch (choice) {
                case "1" -> {
                    System.out.println("Running sort01...");
                    System.out.println("Choose order: (0-ascending, 1-descending)");
                    order = scanner.nextInt();
                    boolean orderBool = true;
                    if (order == 1) orderBool = false;
                    Double[] sorted1 = new Sorter().sort01(array, orderBool);
                    System.out.println("Sorted array (sort01):");
                    for (int i = 0; i < sorted1.length; i++) {
                        System.out.print(sorted1[i] + " ");
                    }
                    System.out.println();
                }
                case "2" -> {
                    System.out.println("\nRunning sort02...");
                    Sorter sorter = new Sorter();
                    System.out.println("Choose order: (0-ascending, 1-descending)");
                    order = scanner.nextInt();
                    sorter.order = order != 1;

                    Double[] sorted2 = sorter.sort02(array);
                    System.out.println("Sorted array (sort02):");
                    for (Double aDouble : sorted2) {
                        System.out.print(aDouble+" ");
                    }
                    System.out.println();
                }
                case "3" -> {
                    System.out.println("\nRunning sort03...");
                    Sorter sorter03 = new Sorter();
                    sorter03.sort03();
                    System.out.println("Sorted array (sort03):");
                    for (int i = 0; i < sorter03.a.length; i++) {
                        System.out.print(sorter03.b[i] + " ");
                    }
                    System.out.println();
                }
                case "4" -> {
                    System.out.println("\nRunning sort04...");
                    Sorter sorter4 = new Sorter();
                    sorter4.a = array;
                    System.out.println("Choose order: (0-ascending, 1-descending)");
                    order = scanner.nextInt();
                    boolean orderBool = true;
                    if (order == 1) orderBool = false;
                    sorter4.order = orderBool;
                    sorter4.sort04();

                    if (sorter4.b != null && sorter4.b.length > 0) {
                        System.out.println("Sorted result (from sort04):");
                        for (int i = 0; i < sorter4.b.length; i++) {
                            System.out.println(sorter4.b[i] + " ");
                        }
                        System.out.println();
                    }
                }
                default -> {
                    System.out.println("You have chosen wrong option.");
                    System.exit(0);
                }
            }
        }

//                benchmarkSorts();
    }
    public static void benchmarkSorts() {
        int size = 1000000;
        Double[] originalArray = new Double[size];
        Sorter sorter1 = new Sorter();
        Sorter sorter2 = new Sorter();
        Sorter sorter4 = new Sorter();


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("sort_results.csv"))) {
            writer.write("Method,Order,Time(ms)");
            writer.newLine();
            double timeSort01 = 0.0;
            double timeSort02 = 0.0;
            double timeSort04 = 0.0;
            for (boolean orderValue : new boolean[]{false, true}) {

                for (int j = 0; j < 100; j++) {
                    for (int i = 0; i < size; i++) {
                        originalArray[i] = Math.random() * 1000;
                    }

                    System.out.println("\n=== Testing with order = " + orderValue + " ===");
                    // sort01
                    Double[] array1 = originalArray.clone();
                    long start1 = System.nanoTime();
                    sorter1.sort01(array1, orderValue);
                    long end1 = System.nanoTime();
                    double time1 = (end1 - start1) / 1_000_000.0;
                    writer.write("sort01," + orderValue + "," + time1);
                    writer.newLine();

                    // sort02
                    Double[] array2 = originalArray.clone();
                    sorter2.order = orderValue;
                    long start2 = System.nanoTime();
                    sorter2.sort02(array2);
                    long end2 = System.nanoTime();
                    double time2 = (end2 - start2) / 1_000_000.0;
                    writer.write("sort02," + orderValue + "," + time2);
                    writer.newLine();

                    // sort04
                    sorter4.a = originalArray.clone();
                    sorter4.order = orderValue;
                    long start4 = System.nanoTime();
                    sorter4.sort04();
                    long end4 = System.nanoTime();
                    double time4 = (end4 - start4) / 1_000_000.0;
                    writer.write("sort04," + orderValue + "," + time4);
                    writer.newLine();

                    timeSort01+=time1;
                    timeSort02+=time2;
                    timeSort04+=time4;

                }


            writer.write("avgSort01," + orderValue + "," + timeSort01/100);
            writer.newLine();
            writer.write("avgSort02," + orderValue + "," + timeSort02/100);
            writer.newLine();
            writer.write("avgSsort04," + orderValue + "," + timeSort04/100);
            writer.newLine();

            timeSort01+=0;
            timeSort02+=0;
            timeSort04+=0;
        }

        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu do pliku: " + e.getMessage());
        }
    }

    public void multi04() {
        if (a == null || order == null) {
            System.err.println("brak danych do sortowania");
            return;
        }

        b = new Double[a.length];
        System.arraycopy(a, 0, b, 0, a.length);

        for (int i = 0; i < b.length - 1; i++) {
            for (int j = 0; j < b.length - i - 1; j++) {
                boolean shouldSwap;
                if (order) {
                    shouldSwap = b[j] > b[j + 1];
                } else {
                    shouldSwap = b[j] < b[j + 1];
                }

                if (shouldSwap) {
                    Double temp = b[j];
                    b[j] = b[j + 1];
                    b[j + 1] = temp;
                }
            }
        }

    }

    public void showGuiDialog() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            javax.swing.JFrame frame = new javax.swing.JFrame("Okno wprowadzania danych");
            frame.setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(400, 300);
            frame.setLayout(new java.awt.BorderLayout());

            javax.swing.JTextField inputField = new javax.swing.JTextField();
            javax.swing.JCheckBox orderCheck = new javax.swing.JCheckBox("Sortuj rosnąco (true/false)");

            javax.swing.JButton submitButton = new javax.swing.JButton("Zatwierdź");
            submitButton.addActionListener(e -> {
                try {
                    String[] parts = inputField.getText().split(",");
                    Double[] inputArray = new Double[parts.length];
                    for (int i = 0; i < parts.length; i++) {
                        inputArray[i] = Double.parseDouble(parts[i].trim());
                    }
                    this.a = inputArray;
                    this.order = orderCheck.isSelected();
                    frame.dispose();
                } catch (Exception ex) {
                    javax.swing.JOptionPane.showMessageDialog(frame, "Błędny input: " + ex.getMessage());
                }
            });

            javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.GridLayout(3, 1));
            panel.add(new javax.swing.JLabel("Wprowadź dane rodzielone przecinkami:"));
            panel.add(inputField);
            panel.add(orderCheck);

            frame.add(panel, java.awt.BorderLayout.CENTER);
            frame.add(submitButton, java.awt.BorderLayout.SOUTH);

            frame.setVisible(true);

        });

        try {
            while (this.a == null || this.order == null) {
                Thread.sleep(200);
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public native Double[] sort01(Double[] a, Boolean order);
    public native Double[] sort02(Double[] a);
    public native void sort03();
    public native void sort04();
}