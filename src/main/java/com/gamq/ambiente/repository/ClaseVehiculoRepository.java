package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.ClaseVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClaseVehiculoRepository extends JpaRepository<ClaseVehiculo, Long> {
    Optional<ClaseVehiculo> findByUuid(String uuid);
    @Query("SELECT c FROM ClaseVehiculo c WHERE LOWER(rtrim(ltrim(c.nombre))) = LOWER(rtrim(ltrim(:nombre)))")
    Optional<ClaseVehiculo> findByNombre(@Param("nombre") String nombre);
    @Query("SELECT case when count(c) > 0 then true else false end FROM ClaseVehiculo c WHERE lower(rtrim(ltrim(c.nombre))) = lower(rtrim(ltrim(:nombre))) AND c.uuid <> :uuidClaseVehiculo")
    boolean exitsClaseVehiculoLikeNombre(@Param("nombre") String nombre,
                                         @Param("uuidClaseVehiculo") String uuidClaseVehiculo);
}
