package RestProject1.RestAPI.repositories;

import RestProject1.RestAPI.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;


@Repository
public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {

    int countByRainingEquals(boolean bol);
}
