package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.DatoTecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DatoTecnicoRepository extends JpaRepository<DatoTecnico, Long> {
    Optional<DatoTecnico> findByUuid(String uuid);
    @Query("SELECT d FROM DatoTecnico d WHERE d.vehiculo.uuid = :uuidVehiculo")
    Optional<DatoTecnico> findByUuidVehiculo(@Param("uuidVehiculo") String uuidVehiculo);
    @Query("SELECT case when count(d) > 0 then true else false end FROM DatoTecnico d WHERE d.tipoClaseVehiculo.uuid = :uuidTipoClaseVehiculo")
    boolean exitsDatoTecnicoUuidTipoClaseVehiculo(@Param("uuidTipoClaseVehiculo") String uuidTipoClaseVehiculo);
}
