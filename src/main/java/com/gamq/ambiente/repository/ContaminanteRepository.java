package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Contaminante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContaminanteRepository extends JpaRepository<Contaminante, Long> {
    Optional<Contaminante> findByUuid(String uuid);
    @Query("SELECT c FROM Contaminante c  WHERE LOWER(rtrim(ltrim(c.nombre))) = LOWER(rtrim(ltrim(:nombre)))")
    Optional<Contaminante> findByNombre(@Param("nombre") String nombre);
    @Query("SELECT case when count(c) > 0 then true else false end FROM Contaminante c WHERE lower(rtrim(ltrim(c.nombre))) = lower(rtrim(ltrim(:nombre))) AND c.uuid <> :uuidContaminante")
    boolean exitsContaminanteLikeNombre(@Param("nombre") String nombre,
                                       @Param("uuidContaminante") String uuidContaminante);
}
