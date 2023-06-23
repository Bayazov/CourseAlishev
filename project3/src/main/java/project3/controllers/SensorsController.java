package project3.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import project3.dto.SensorDTO;
import project3.models.Sensor;
import project3.services.SensorService;
import project3.util.SensorErrorResponse;
import project3.util.SensorNotCreatedException;
import project3.util.SensorValidator;

import java.util.List;

@RestController
@RequestMapping("sensors")
public class SensorsController {

    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;
    private final SensorService sensorService;



    @Autowired
    public SensorsController(ModelMapper modelMapper, SensorValidator sensorValidator, SensorService sensorService) {
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
        this.sensorService = sensorService;
    }

    @PostMapping("registration")
    public ResponseEntity<HttpStatus> sensorRegistration(@RequestBody @Valid SensorDTO sensorDTO,
                                                         BindingResult bindingResult){

        sensorValidator.validate(convertToSensor(sensorDTO),bindingResult);
        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();

            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new SensorNotCreatedException(errorMsg.toString());
        }

        sensorService.save(convertToSensor(sensorDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e){
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage()
        );
        // В HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // NOT_FOUND - 404 статус
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
