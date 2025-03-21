package pl.edu.pwr.algorithms;

import pl.edu.pwr.models.Measurement;

import java.util.Map;

public class AverageAlgorithm implements IAlgorithm {
    private final Map<String, Measurement> values;
    private final int firstIndex;
    private final int lastIndex;
    private final int size;

    public AverageAlgorithm(Map<String, Measurement> values, int firstIndex, int lastIndex) {
        this.values = values;
        this.firstIndex = firstIndex;
        this.lastIndex = lastIndex;
        this.size = lastIndex - firstIndex + 1;
    }


    public float[] calculate() {
        int sumTemp = 0;
        int sumHum = 0;
        int sumPre = 0;
        for (int i = firstIndex; i <= lastIndex; i++) {
            if (values.containsKey(String.valueOf(i))) {
                Measurement measurement = values.get(String.valueOf(i));
                sumPre += measurement.pressure();
                sumHum += measurement.humidity();
                sumTemp += measurement.temperature();
            }
        }
        float avgTemp = (float) sumTemp / size;
        float avgPre = (float) sumPre / size;
        float avgHum = (float) sumHum / size;


        return new float[]{avgTemp, avgPre, avgHum};
    }

    public String getResults() {
        StringBuilder stats = new StringBuilder();

        float[] avgRes = calculate();
        stats.append(String.format("Average Temperature: %.2f°C%n", avgRes[0]));
        stats.append(String.format("Average Humidity: %.2f%%%n", avgRes[2]));
        stats.append(String.format("Average Pressure: %.2f hPa%n", avgRes[1]));

        return stats.toString();
    }
}
