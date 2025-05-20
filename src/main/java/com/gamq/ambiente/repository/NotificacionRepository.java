package com.gamq.ambiente.repository;

import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.enumeration.TipoNotificacion;
import com.gamq.ambiente.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    Optional<Notificacion> findByUuid(String uuid);
    Optional<Notificacion> findByNumeroNotificacion(String numeroNotificacion);
    int countByInspeccion_UuidAndStatusNotificacionIn(String uuid, List<EstadoNotificacion> estados);
    @Query("SELECT case when count(n) > 0 then true else false end FROM Notificacion n WHERE lower(n.numeroNotificacion) = lower(:numeroNotificacion) AND n.uuid <> :uuidNotificacion")
    boolean exitsNotificacionLikeNumeroNotificacion(@Param("numeroNotificacion") String numeroNotificacion,
                                                    @Param("uuidNotificacion") String uuidNotificacion);
    List<Notificacion> findByTypeNotificacion(TipoNotificacion typeNotificacion);
}
