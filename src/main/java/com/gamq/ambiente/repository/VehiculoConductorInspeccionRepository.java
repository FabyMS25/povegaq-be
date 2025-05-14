package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.VehiculoConductorInspeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehiculoConductorInspeccionRepository extends JpaRepository<VehiculoConductorInspeccion, Long> {
    Optional<VehiculoConductorInspeccion> findByUuid(String uuid);
    @Query("SELECT i FROM VehiculoConductorInspeccion i WHERE i.inspeccion.uuid = :uuidInspeccion ")
    Optional<VehiculoConductorInspeccion> findByUuidInspeccion(String uuidInspeccion);
    @Query("SELECT COUNT(vci) > 0 FROM VehiculoConductorInspeccion vci WHERE vci.vehiculo.uuid = :uuidVehiculo AND vci.conductor.uuid = :uuidConductor AND vci.inspeccion.uuid = :uuidInspeccion")
    boolean existsByVehiculoAndConductorAndInspeccion(@Param("uuidVehiculo") String uuidVehiculo,
                                                      @Param("uuidConductor") String uuidConductor,
                                                      @Param("uuidInspeccion") String uuidInspeccion);
}
