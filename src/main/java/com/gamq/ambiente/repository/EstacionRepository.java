package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Estacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstacionRepository extends JpaRepository<Estacion, Long> {
    Optional<Estacion> findByUuid(String uuid);
    @Query("SELECT e FROM Estacion e  WHERE LOWER(rtrim(ltrim(e.nombre))) = LOWER(rtrim(ltrim(:nombre)))")
    Optional<Estacion> findByNombre(@Param("nombre") String nombre);
    @Query("SELECT case when count(e) > 0 then true else false end FROM Estacion e WHERE lower(rtrim(ltrim(e.nombre))) = lower(rtrim(ltrim(:nombre))) AND e.uuid <> :uuidEstacion")
    boolean exitsEstacionLikeNombre(@Param("nombre") String nombre,
                                    @Param("uuidEstacion") String uuidEstacion);
}
