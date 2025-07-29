package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.CategoriaAire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoriaAireRepository extends JpaRepository<CategoriaAire, Long> {
    @Query("SELECT c FROM CategoriaAire c WHERE c.uuid = :uuid AND c.estado = false")
    Optional<CategoriaAire> findByUuid(String uuid);
    @Query("SELECT c FROM CategoriaAire c WHERE c.activo = true AND  LOWER(rtrim(ltrim(c.categoria))) = LOWER(rtrim(ltrim(:categoria)))")
    Optional<CategoriaAire> findByCategoria(@Param("categoria") String categoria);
    @Query("SELECT c FROM CategoriaAire c WHERE c.activo = true AND LOWER(rtrim(ltrim(c.color))) = LOWER(rtrim(ltrim(:color)))")
    Optional<CategoriaAire> findByColor(@Param("color") String color);
    @Query("SELECT case when count(c) > 0 then true else false end FROM CategoriaAire c WHERE lower(rtrim(ltrim(c.categoria))) = lower(rtrim(ltrim(:categoria))) AND lower(rtrim(ltrim(c.color))) = lower(rtrim(ltrim(:color))) AND c.uuid <> :uuidCategoria")
    boolean exitsCategoriaAireLikeCategoriaAndColor(@Param("categoria") String categoria,
                                                    @Param("color") String color,
                                                    @Param("uuidCategoria") String uuidCategoria);
    List<CategoriaAire> findByActivoTrue();

 }
