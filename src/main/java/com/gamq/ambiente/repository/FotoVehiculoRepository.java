package com.gamq.ambiente.repository;

import com.gamq.ambiente.dto.FotoVehiculoDto;
import com.gamq.ambiente.model.FotoVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FotoVehiculoRepository extends JpaRepository<FotoVehiculo, Long> {
    Optional<FotoVehiculo> findByUuid(String uuid);
    @Query("SELECT f FROM FotoVehiculo f WHERE f.vehiculo.uuid = ?1")
    List<FotoVehiculo> findByUuidVehiculo(String uuidVehiculo);
}
