package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.GrupoRiesgo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GrupoRiesgoRepository extends JpaRepository<GrupoRiesgo, Long> {
    Optional<GrupoRiesgo> findByUuid(String uuid);
    @Query("SELECT g FROM GrupoRiesgo g WHERE LOWER(rtrim(ltrim(g.grupo))) = LOWER(rtrim(ltrim(:grupo)))")
    List<GrupoRiesgo> findByGrupo(@Param("grupo") String grupo);
    @Query("SELECT g FROM GrupoRiesgo g WHERE LOWER(rtrim(ltrim(g.grupo))) = LOWER(rtrim(ltrim(:grupo))) AND  g.categoriaAire.uuid = :uuidCategoriaAire")
    Optional<GrupoRiesgo> findByGrupoAndCategoriaAire(@Param("grupo") String grupo,
                                                      @Param("uuidCategoriaAire") String uuidCategoriaAire);
    @Query("SELECT case when count(g) > 0 then true else false end FROM GrupoRiesgo g WHERE lower(rtrim(ltrim(g.grupo))) = lower(rtrim(ltrim(:grupo))) AND g.uuid <> :uuidGrupo")
    boolean exitsGrupoRiesgoLikeGrupo(@Param("grupo") String grupo,
                                      @Param("uuidGrupo") String uuidGrupo);
    @Query("SELECT g FROM GrupoRiesgo g WHERE g.categoriaAire.uuid = :uuidCategoriaAire")
    List<GrupoRiesgo> findByUuidCategoriaAire(@Param("uuidCategoriaAire") String uuidCategoriaAire);
}
