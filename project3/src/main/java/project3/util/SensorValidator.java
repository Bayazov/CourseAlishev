package project3.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import project3.models.Sensor;
import project3.services.SensorService;

@Component
public class SensorValidator implements Validator {

    private final SensorService sensorService;
    @Autowired
    public SensorValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return Sensor.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if(sensorService.findByName(sensor.getName()).isPresent())
            errors.rejectValue("name","","Sensor with this name already exist");

    }
}
