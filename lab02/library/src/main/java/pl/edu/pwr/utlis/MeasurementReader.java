package pl.edu.pwr.utlis;

import pl.edu.pwr.models.Measurement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class MeasurementReader {

    private static final String COMMA_DELIMITER = ",";
    private final String fileName;
    private HashMap<String, Measurement> measurementDataHashMap;

    public MeasurementReader(String fileName) {
        this.fileName = fileName;
        this.measurementDataHashMap = new HashMap<>();
        measurementDataHashMap = readFile();
    }

    public HashMap<String, Measurement> readFile() {
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
        return measurementDataHashMap;
    }

    private void addRecord(String[] values){
        try {
            Integer id = Integer.parseInt(values[0]);
            measurementDataHashMap.put(values[0], new Measurement(
                    id, values[1], Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4])
            ));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Measurement> getMeasurementDataHashMap() {
        return measurementDataHashMap;
    }
}
