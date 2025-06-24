package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.VehiculoTipoCombustible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehiculoTipoCombustibleRepository extends JpaRepository<VehiculoTipoCombustible, Long> {
    Optional<VehiculoTipoCombustible> findByUuid(String uuid);
    @Query(value = "SELECT * FROM vehiculo_tipo_combustible WHERE uuid = :uuid", nativeQuery = true)
    Optional<VehiculoTipoCombustible> findByUuidIncluyendoEliminados(@Param("uuid") String uuid);
}
