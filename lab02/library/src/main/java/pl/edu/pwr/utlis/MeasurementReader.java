package pl.edu.pwr.utlis;

import pl.edu.pwr.models.Measurement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class MeasurementReader {

    private static final String COMMA_DELIMITER = ",";
    private final String fileName;
    private HashMap<String, Measurement> measurementDataWeakHashMap;

    public MeasurementReader(String fileName) {
        this.fileName = fileName;
        this.measurementDataWeakHashMap = new HashMap<>();
        measurementDataWeakHashMap = readFile();
    }

    public HashMap<String, Measurement> readFile(){
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] values = line.split(COMMA_DELIMITER);
                try {
                    Integer id = Integer.parseInt(values[0]);
                    measurementDataWeakHashMap.put(values[0], new Measurement(
                            id, values[1], Float.parseFloat(values[2]), Float.parseFloat(values[3]), Float.parseFloat(values[4])
                    ));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  measurementDataWeakHashMap;
    }

    private Measurement readRecordById(int searchId) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] values = line.split(COMMA_DELIMITER);
                try {
                    int id = Integer.parseInt(values[0]);

                    if (id == searchId) {
                        measurementDataWeakHashMap.put(new String(String.valueOf(id)), new Measurement(
                                id, values[1], Float.parseFloat(values[2]),
                                Float.parseFloat(values[3]), Float.parseFloat(values[4])));
                        return measurementDataWeakHashMap.get(String.valueOf(id));
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HashMap<String, Measurement> getMeasurementDataWeakHashMap() {
        return measurementDataWeakHashMap;
    }
}
