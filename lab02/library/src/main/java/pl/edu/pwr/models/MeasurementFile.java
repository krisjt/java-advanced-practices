package pl.edu.pwr.models;

import pl.edu.pwr.utlis.MeasurementReader;

import java.util.HashMap;

public class MeasurementFile {
    private String filepath;
    private HashMap<String, Measurement> records;
    private boolean isInMemory;

    public MeasurementFile(String filepath) {
        this.filepath = filepath;
        fillMap();
    }

    private void fillMap(){
        MeasurementReader measurementReader = new MeasurementReader(filepath);
        records = measurementReader.getMeasurementDataWeakHashMap();
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public HashMap<String, Measurement> getRecords(){
        return records;
    }

    public HashMap<String,Measurement> getRecords(int first, int last){
        HashMap<String,Measurement> measurementHashMap= new HashMap<>();
        for (int i = first; i <= last; i++) {
            if(records.containsKey(String.valueOf(i))) {
                measurementHashMap.put(String.valueOf(i),records.get(String.valueOf(i)));
            }
        }
        return measurementHashMap;
    }

    public String getDataString(int first, int last) {
        StringBuilder content = new StringBuilder();
        content.append(String.format("%20s | %20s | %20s | %20s | %20s | %n",
                "ID", "Hour", "Temperature", "Humidity", "Pressure"));

        for (int i = first; i <= last; i++) {
            if (records.containsKey(String.valueOf(i))) {
                Measurement value = records.get(String.valueOf(i));
                content.append(String.format("%20s | %20s | %20s | %20s | %20s | %n",
                        value.getId(), value.getHour(), value.getTemperature(),
                        value.getHumidity(), value.getPressure()));
            }
        }
        return content.toString();
    }


    public void setRecords(HashMap<String, Measurement> records) {
        this.records = records;
    }

    @Override
    public String toString() {
        return "File{" +
                "filepath='" + filepath + '\'' +
                ", records=" + records +
                '}';
    }

    public boolean isInMemory() {
        return isInMemory;
    }

    public void setInMemory(boolean inMemory) {
        isInMemory = inMemory;
    }
}
