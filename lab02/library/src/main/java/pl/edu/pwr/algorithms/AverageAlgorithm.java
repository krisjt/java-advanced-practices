package pl.edu.pwr.algorithms;

import pl.edu.pwr.models.Measurement;

import java.util.HashMap;

public class AverageAlgorithm implements IAlgorithm{
    private HashMap<String, Measurement> values;
    private int firstIndex = 1;
    private int lastIndex;
    private int size;

    public AverageAlgorithm(HashMap<String, Measurement> values, int firstIndex, int lastIndex) {
        this.values = values;
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
        this.size = lastIndex - firstIndex + 1;
    }

    public AverageAlgorithm(HashMap<String, Measurement> values, int lastIndex) {
        this.values = values;
        this.lastIndex = lastIndex;
        this.size = lastIndex - firstIndex + 1;
    }

    public AverageAlgorithm(HashMap<String, Measurement> values) {
        this.values = values;
        this.lastIndex = values.size();
        this.size = lastIndex - firstIndex + 1;
    }

    public float[] calculate(){
        int sumTemp = 0;
        int sumHum = 0;
        int sumPre = 0;
        for(int i = firstIndex; i <= lastIndex; i++){
            if(values.containsKey(String.valueOf(i))) {
                Measurement measurement = values.get(String.valueOf(i));
                sumPre += measurement.getPressure();
                sumHum += measurement.getHumidity();
                sumTemp += measurement.getTemperature();
            }
        }
        float avgTemp = (float) sumTemp/size;
        float avgPre = (float) sumPre/size;
        float avgHum = (float) sumHum/size;


        return new float[]{avgTemp,avgPre,avgHum};
    }

    public String getResults(){
        StringBuilder stats = new StringBuilder();

        float[] avgRes = calculate();
        stats.append(String.format("Average Temperature: %.2f°C%n", avgRes[0]));
        stats.append(String.format("Average Humidity: %.2f%%%n", avgRes[2]));
        stats.append(String.format("Average Pressure: %.2f hPa%n", avgRes[1]));

        return stats.toString();
    }
}
