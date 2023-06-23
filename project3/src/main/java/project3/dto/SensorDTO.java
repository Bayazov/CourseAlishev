package project3.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class SensorDTO {

    @NotEmpty(message = "Name of sensor should not be empty")
    @Size(min = 1, max = 50, message = "Size of name should be between 1 and 50 symbols")
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
