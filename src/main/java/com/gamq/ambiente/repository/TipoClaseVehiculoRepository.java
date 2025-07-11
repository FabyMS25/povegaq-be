package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.TipoClaseVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoClaseVehiculoRepository extends JpaRepository<TipoClaseVehiculo, Long> {
    Optional<TipoClaseVehiculo> findByUuid(String uuid);
    @Query("SELECT t FROM TipoClaseVehiculo t WHERE LOWER(rtrim(ltrim(t.nombre))) = LOWER(rtrim(ltrim(:nombre)))")
    Optional<TipoClaseVehiculo> findByNombre(@Param("nombre") String nombre);
    @Query("SELECT case when count(t) > 0 then true else false end FROM TipoClaseVehiculo t WHERE lower(rtrim(ltrim(t.nombre))) = lower(rtrim(ltrim(:nombre))) AND t.uuid <> :uuidTipoClaseVehiculo")
    boolean exitsTipoClaseVehiculoLikeNombre(@Param("nombre") String nombre,
                                             @Param("uuidTipoClaseVehiculo") String uuidTipoClaseVehiculo);
}
