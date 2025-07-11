package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.TipoParametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TipoParametroRepository extends JpaRepository<TipoParametro, Long> {
    @Query("SELECT t FROM TipoParametro t WHERE t.uuid = :uuid AND t.estado = false")
    Optional<TipoParametro> findByUuid(String uuid);
    @Query("SELECT t FROM TipoParametro t  WHERE LOWER(rtrim(ltrim(t.nombre))) = LOWER(rtrim(ltrim(:nombre))) AND t.estado = false")
    Optional<TipoParametro> findByNombre(@Param("nombre") String nombre);
    @Query("SELECT case when count(t) > 0 then true else false end FROM TipoParametro t WHERE lower(rtrim(ltrim(t.nombre))) = lower(rtrim(ltrim(:nombre))) AND t.activo = true AND t.uuid <> :uuid AND t.estado = false")
    boolean exitsTipoParametroLikeNombre(@Param("nombre") String nombre,
                                         @Param("uuid") String uuid);
    @Query("SELECT t FROM TipoParametro t WHERE t.activo = true AND t.estado = false")
    List<TipoParametro> findAllActivoTrue();

    boolean existsByNombreAndActivoTrue(@Param("nombre") String nombre);
}
