package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.FotoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FotoVehiculoRepository extends JpaRepository<FotoVehiculo, Long> {
    Optional<FotoVehiculo> findByUuid(String uuid);
}
