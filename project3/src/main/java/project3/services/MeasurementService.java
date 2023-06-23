package project3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project3.models.Measurement;
import project3.repositories.MeasurementsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementsRepository measurementsRepository;

    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementsRepository measurementsRepository, SensorService sensorService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorService = sensorService;
    }

    @Transactional
    public void save(Measurement measurement){
        measurement.setCreatedAt(LocalDateTime.now());
        measurementsRepository.save(measurement);
    }

    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }

    public int findCountRainyDays(){
        List<Measurement> all = measurementsRepository.findAll();
        int res = 0;
        for(Measurement measurement: all){
            if(measurement.isRaining() == true)
                res++;
        }
        return res;
    }
}
