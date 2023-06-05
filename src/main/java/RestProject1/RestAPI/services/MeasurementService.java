package RestProject1.RestAPI.services;

import RestProject1.RestAPI.models.Measurement;
import RestProject1.RestAPI.models.Sensor;
import RestProject1.RestAPI.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }

    public List<Measurement> all(){
        return measurementRepository.findAll();
    }

    public int getAllRainingDays(){
        return measurementRepository.countByRainingEquals(true);
    }

    @Transactional
    public void add(Measurement measurement){

        Sensor sensor = sensorService.findByName(measurement.getSensor().getName()).get();
        measurement.setSensor(sensor);
        measurement.setNameSensor(sensor.getName());
        measurement.setCreatedAt(LocalDateTime.now());

        if(sensor.getMeasurements()==null){
            sensor.setMeasurements(new ArrayList<>(Collections.singletonList(measurement)));
        } else {
            sensor.getMeasurements().add(measurement);
        }
        measurementRepository.save(measurement);
    }
}
