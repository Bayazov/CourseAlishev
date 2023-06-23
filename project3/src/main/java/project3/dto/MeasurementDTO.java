package project3.dto;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import project3.models.Sensor;

public class MeasurementDTO {


    @Min(value = -100, message = "Value should be greater than -100")
    @Max(value = 100, message = "Value should be lower than 100")
    private float value;


    private boolean raining;


    @NotNull(message = "Sensor of measurement can not be empty")
    private SensorDTO sensor;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    @Override
    public String toString() {
        return "MeasurementDTO{" +
                "value=" + value +
                ", raining=" + raining +
                ", sensor=" + sensor +
                '}';
    }
}
