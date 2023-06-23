package project3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project3.models.Measurement;
import project3.models.Sensor;

import java.util.Optional;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {
    Optional<Measurement> findBySensor(Sensor sensor);
}
