package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    Optional<Alerta> findByUuid(String uuid);
    @Query("SELECT a FROM Alerta a WHERE FUNCTION('DATE', a.fechaAlerta) = :fechaActual AND a.uuidDestinatario =:uuidDestinatario")
    List<Alerta> findByFechaAlertaAndUuidDestinatario(@Param("fechaActual") Date fechaActual,
                                                      @Param("uuidDestinatario") String uuidDestinatario);
    @Query("SELECT a FROM Alerta a WHERE a.notificacion.uuid =:uuidNotificacion")
    Optional<Alerta> findByUuidNotificacion(@Param("uuidNotificacion") String uuidNotificacion);
    @Query("SELECT a FROM Alerta a WHERE FUNCTION('DATE', a.fechaAlerta) = :fechaActual")
    List<Alerta> findByFechaAlerta(@Param("fechaActual") Date fechaActual);

}
