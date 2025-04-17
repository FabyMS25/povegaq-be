package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.TipoInfraccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Repository
public interface TipoInfraccionRepository extends JpaRepository<TipoInfraccion, Long> {
    Optional<TipoInfraccion> findByUuid(String uuid);
    @Query("SELECT t FROM TipoInfraccion t  WHERE LOWER(t.grado) = LOWER(:grado)")
    Optional<TipoInfraccion> findByGrado(@Param("grado") String grado);
    @Query("SELECT t FROM TipoInfraccion t WHERE t.tipoContribuyente.uuid = :uuidTipoContribuyente AND (t.fechaInicio<= :fechaActual AND (t.fechaFin IS NULL OR t.fechaFin >= :fechaActual))")
    Optional<TipoInfraccion> findMostRecentTipoInfraccionByFechaActualAndUuidTipoContribuyente(@Param("uuidTipoContribuyente") String uuidTipoContribuyente,
                                                                                               @Param("fechaActual") Date fechaActual);

    @Query("SELECT COUNT(t) > 0 FROM TipoInfraccion t WHERE t.fechaInicio >= :fechaActual AND (t.fechaFin IS NULL OR t.fechaFin <= :fechaActual) AND t.tipoContribuyente.uuid = :uuidTipoContribuyente AND t.uuid <> :uuidTipoInfraccion")
    boolean existsFechaActualForUuidTipoContribuyente(@Param("fechaActual") Date fechaActual,
                                                 @Param("uuidTipoContribuyente") String uuidTipoContribuyente,
                                                 @Param("uuidTipoInfraccion") String uuidTipoInfraccion);

    //Optional<TipoInfraccion> findByGrado(String grado);
 /*   @Query(value = "SELECT monto FROM valores_infraccion " +
            "WHERE id_tipo_contribuyente = :idTipoContribuyente " +
            "AND grado_infraccion = :gradoInfraccion " +
            "AND fecha_inicio <= :fechaInfraccion " +
            "AND (fecha_fin IS NULL OR fecha_fin >= :fechaInfraccion) " +
            "ORDER BY fecha_inicio DESC LIMIT 1",
            nativeQuery = true)
    BigDecimal findvalorUFVByInfraccionNative(
            @Param("uuidTipoContribuyente") String uuidTipoContribuyente,
            @Param("gradoInfraccion") String gradoInfraccion,
            @Param("fechaInfraccion") Date fechaInfraccion);*/

}
