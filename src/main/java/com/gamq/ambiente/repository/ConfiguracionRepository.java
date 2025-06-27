package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Actividad;
import com.gamq.ambiente.model.Configuracion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConfiguracionRepository extends JpaRepository<Configuracion, Long> {
    @Query("SELECT c FROM Configuracion c WHERE c.uuid = :uuid AND c.estado = false")
    Optional<Configuracion> findByUuid(String uuid);

    @Query("SELECT c FROM Configuracion c  WHERE LOWER(rtrim(ltrim(c.clave))) = LOWER(rtrim(ltrim(:clave)))")
    Optional<Configuracion> findByClave(@Param("clave") String clave);
    @Query("SELECT case when count(c) > 0 then true else false end FROM Configuracion c WHERE c.activo = true AND lower(rtrim(ltrim(c.clave))) = lower(rtrim(ltrim(:clave))) AND c.uuid <> :uuidConfiguracion")
    boolean exitsConfiguracionLikeClave(@Param("clave") String clave,
                                        @Param("uuidConfiguracion") String uuidConfiguracion);

    @Query("SELECT c FROM Configuracion c WHERE YEAR(c.fechaInicio) = :year AND c.estado = false ")
    List<Configuracion> findConfiguracionesPorAnio(Integer year);
    List<Configuracion> findByActivoTrueOrderByFechaInicioAsc();
    @Query("SELECT c FROM Configuracion c WHERE c.activo = true AND c.fechaInicio <= :rangoFin AND c.fechaFin >= :rangoInicio")
    List<Configuracion> findConfiguracionesBetweenFechas(@Param("rangoInicio") Date rangoInicio, @Param("rangoFin") Date rangoFin);
}
