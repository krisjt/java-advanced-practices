package pl.edu.pwr.models;

public class Measurement {
    private Integer id;
    private String hour;
    private float pressure;
    private float temperature;
    private float humidity;

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

    public Measurement(Integer id, String hour, float temperature, float humidity, float pressure) {
        this.id = id;
        this.hour = hour;
        this.pressure = pressure;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public float getPressure() {
        return pressure;
    }

    public void setPressure(float pressure) {
        this.pressure = pressure;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }
}
