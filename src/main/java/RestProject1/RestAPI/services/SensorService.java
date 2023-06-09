package RestProject1.RestAPI.services;

import RestProject1.RestAPI.models.Sensor;
import RestProject1.RestAPI.repositories.SensorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void registration(Sensor sensor) {
        sensorRepository.save(sensor);
    }

    public Optional<Sensor> findByName(String name){
        return sensorRepository.findByName(name);
    }
}
