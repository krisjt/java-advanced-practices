package pl.edu.pwr.service;

import pl.edu.pwr.service.Values;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    private static final String COMMA_DELIMITER = ",";
    private final String fileName;
    private List<Values> valuesList;

    public CsvReader(String fileName) {
        this.fileName = fileName;
        this.valuesList = new ArrayList<>();
        valuesList = readFile();
    }

    public List<Values> readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] values = line.split(COMMA_DELIMITER);
                addRecord(values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return valuesList;
    }

        private void addRecord(String[] values){
            try {
                valuesList.add(new Values(Integer.parseInt(values[0]),Integer.parseInt(values[1]),Integer.parseInt(values[2]))
                );
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        public List<Values> getValuesList() {
            return valuesList;
        }

}
