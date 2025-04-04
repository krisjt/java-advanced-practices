package pl.edu.pwr.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CsvReader {

    private static final String COMMA_DELIMITER = ",";
    private final String fileName;
    private String[][] data;
    private String[] header;
    private int size;

    public CsvReader(String fileName) {
        this.fileName = fileName;
    }

    public void readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            int i = 0;

            String line1 = br.readLine();
            String[]values1 = line1.split(COMMA_DELIMITER);
            this.size = values1.length;

            data = new String[size][size];
            header = values1;
            while ((line = br.readLine()) != null) {

                String[] values = line.split(COMMA_DELIMITER);

                data[i] = values;
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[][] getData() {
        return data;
    }

    public String[] getHeader() {
        return header;
    }
}
