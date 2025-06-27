package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Actividad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Long> {
    @Query("SELECT a FROM Actividad a WHERE a.uuid = :uuid AND a.estado = false")
    Optional<Actividad> findByUuid(String uuid);
    @Query("SELECT a FROM Actividad a  WHERE a.activo = true AND LOWER(rtrim(ltrim(a.tipoActividad))) = LOWER(rtrim(ltrim(:tipoActividad)))")
    Optional<Actividad> findByTipoActividad(@Param("tipoActividad") String tipoActividad);
    @Query("SELECT case when count(a) > 0 then true else false end FROM Actividad a WHERE lower(rtrim(ltrim(a.tipoActividad))) = lower(rtrim(ltrim(:tipoActividad))) AND a.uuid <> :uuidActividad")
    boolean exitsActividadLikeTipoActividad(@Param("tipoActividad") String tipoActividad,
                                            @Param("uuidActividad") String uuidActividad);
    List<Actividad> findByActivoTrueOrderByFechaInicioAsc();

    @Query("SELECT a FROM Actividad a WHERE YEAR(a.fechaInicio) = :year AND a.estado = false ")
    List<Actividad> findActividadesPorAnio(Integer year);

    @Query("SELECT a FROM Actividad a WHERE a.activo = true AND a.fechaInicio <= :rangoFin AND a.fechaFin >= :rangoInicio")
    List<Actividad> findActividadesBetweenFechas(@Param("rangoInicio") Date rangoInicio, @Param("rangoFin") Date rangoFin);
}
