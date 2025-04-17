package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    Optional<Vehiculo> findByUuid(String uuid);
    @Query("SELECT v FROM Vehiculo v  WHERE LOWER(rtrim(ltrim(v.placa))) = LOWER(rtrim(ltrim(:placa)))")
    Optional<Vehiculo> findByPlaca(String placa);
    @Query("SELECT case when count(v) > 0 then true else false end FROM Vehiculo v WHERE lower(rtrim(ltrim(v.placa))) = lower(rtrim(ltrim(:placa))) AND v.uuid <> :uuid")
    boolean exitsVehiculoLikePlaca(@Param("placa") String placa,
                                   @Param("uuid") String uuid);
}
