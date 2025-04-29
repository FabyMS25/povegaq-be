package com.gamq.ambiente.repository;

import com.gamq.ambiente.dto.DetalleInspeccionDto;
import com.gamq.ambiente.model.DetalleInspeccion;
import com.gamq.ambiente.model.TipoParametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetalleInspeccionRepository extends JpaRepository<DetalleInspeccion, Long> {
    Optional<DetalleInspeccion> findByUuid(String uuid);
    @Query("SELECT MAX(d.nroEjecucion) FROM DetalleInspeccion d WHERE d.inspeccion.uuid = :uuidInspeccion")
    Integer findUltimaEjecucionByUuidInspeccion(@Param("uuidInspeccion") String uuidInspeccion);
    @Query("SELECT d FROM DetalleInspeccion d WHERE d.inspeccion.uuid = :uuidInspeccion AND d.nroEjecucion = :nroEjecucion")
    List<DetalleInspeccion> findByUuidInspeccionAndNroEjecucion(@Param("uuidInspeccion") String uuidInspeccion,
                                                                @Param("nroEjecucion") Integer nroEjecucion);

    @Query("SELECT case when count(d) > 0 then true else false end FROM DetalleInspeccion d WHERE d.tipoParametro.uuid = :uuidTipoParametro AND d.inspeccion.uuid = :uuidInspeccion  AND d.nroEjecucion = :nroEjecucion")
    boolean exitsDetalleInspeccionByUuidTipoParametroAndUuidInspeccionAndNroEjecucion(@Param("uuidTipoParametro") String uuidTipoParametro,
                                                                                      @Param("uuidInspeccion") String uuidInspeccion,
                                                                                      @Param("nroEjecucion") Integer nroEjecucion);


    @Query("SELECT case when count(d) > 0 then true else false end FROM DetalleInspeccion d WHERE d.tipoParametro.uuid = :uuidTipoParametro AND d.nroEjecucion = :nroEjecucion AND  d.inspeccion.uuid = :uuidInspeccion AND d.uuid <> :uuidDetalleInspeccion ")
    boolean exitsDetalleInspeccionLike(@Param("uuidTipoParametro") String uuidTipoParametro,
                                       @Param("nroEjecucion") Integer nroEjecucion,
                                       @Param("uuidInspeccion") String uuidInspeccion,
                                       @Param("uuidDetalleInspeccion") String uuidDetalleInspeccio);
    @Query("SELECT d FROM DetalleInspeccion d WHERE d.inspeccion.uuid = :uuidInspeccion ORDER BY d.nroEjecucion ASC")
    List<DetalleInspeccion> findByUuidInspeccion(@Param("uuidInspeccion") String uuidInspeccion);

    List<DetalleInspeccion> findByInspeccionUuidOrderByNroEjecucionAsc(String uuidInspeccion);
}
