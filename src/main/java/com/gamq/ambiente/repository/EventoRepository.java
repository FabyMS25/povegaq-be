package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Actividad;
import com.gamq.ambiente.model.Evento;
import com.gamq.ambiente.model.Ufv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    Optional<Evento> findByUuid(String uuid);
    List<Evento> findByInstitucion(String institucion);
    List<Evento> findByFechaInicioBefore(Date fecha);
//    @Query("SELECT e FROM Evento e WHERE e.fechaInicio >= :fechaInicio AND e.fechaFin <= : fechaFin ORDER BY e.fecha desc")
//    List<Evento> findListEventoByFecha(Date fechaInicio, Date fechaFin);
    @Query("SELECT e FROM Evento e WHERE e.fechaFin > :fecha")
    List<Evento> findEventosActivos(@Param("fecha") Date fecha);
    @Query("SELECT e FROM Evento e WHERE YEAR(e.fechaInicio) = :year AND e.estado = false ")
    List<Evento> findEventosPorAnio(Integer year);
}
