package pl.edu.pwr.models;

public record Measurement(Integer id, String hour, float temperature, float humidity, float pressure) {
    @Override
    public String toString() {
        return "MeasurementData{" +
                "id=" + id +
                ", hour='" + hour + '\'' +
                ", pressure=" + pressure +
                ", temperature=" + temperature +
                ", humidity=" + humidity +
                '}';
    }

}
