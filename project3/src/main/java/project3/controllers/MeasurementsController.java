package project3.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import project3.dto.MeasurementDTO;
import project3.models.Measurement;
import project3.services.MeasurementService;
import project3.services.SensorService;
import project3.util.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("measurements")
public class MeasurementsController {

    private final ModelMapper modelMapper;
    private final MeasurementService measurementService;
    private final SensorService sensorService;

    @Autowired
    public MeasurementsController(ModelMapper modelMapper, MeasurementService measurementService, SensorService sensorService) {
        this.modelMapper = modelMapper;
        this.measurementService = measurementService;
        this.sensorService = sensorService;
    }

    @GetMapping
    public List<MeasurementDTO> getMeasurements(){
        return measurementService.findAll().stream().map(this::convertToMeasurementDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("add")
    public HttpEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                      BindingResult bindingResult){
        Measurement measurement = convertToMeasurement(measurementDTO);
        if(bindingResult.hasErrors()){
            throw new MeasurementNotCreatedException
                    (getMessageError(bindingResult.getFieldErrors()).toString());
        }

        measurementService.save(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("rainyDaysCount")
    public int getRainyDaysCount(){
        return measurementService.findCountRainyDays();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotCreatedException e){
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage()
        );
        // В HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // NOT_FOUND - 404 статус
    }



    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotFoundException e){
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage()
        );
        // В HTTP ответе тело ответа (response) и статус в заголовке
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // NOT_FOUND - 404 статус
    }

    private StringBuilder getMessageError(List<FieldError> errors){
        StringBuilder errorMsg = new StringBuilder();
        for (FieldError error : errors){
            errorMsg.append(error.getField())
                    .append(" - ").append(error.getDefaultMessage())
                    .append(";");
        }
        return errorMsg;
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement,MeasurementDTO.class);
    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO){

        String s = measurementDTO.getSensor().getName();
        if(s.length() < 1 || s.length() > 50)
            throw new SensorNotFoundException("Size of sensor name should be between 1 and 50 symbols");

        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        measurement.setSensor(sensorService.findByName(s).orElse(null));
        if(measurement.getSensor()==null)
            throw new SensorNotFoundException("Sensor with this name does not exist");
        return measurement;
    }
}
