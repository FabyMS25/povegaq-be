package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Equipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipoRepository extends JpaRepository<Equipo, Long> {
    Optional<Equipo> findByUuid(String uuid);
    Optional<Equipo> findByNombre(String nombre);
    @Query("SELECT case when count(e) > 0 then true else false end FROM Equipo e WHERE lower(rtrim(ltrim(e.nombre))) = lower(rtrim(ltrim(:nombre))) AND e.uuid <> :uuidEquipo")
    boolean exitsEquipoLikeNombre(@Param("nombre") String nombre,
                                  @Param("uuidEquipo") String uuidEquipo);
}
