package com.gamq.ambiente.repository;

import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    Optional<Notificacion> findByUuid(String uuid);
    int countByInspeccion_UuidAndStatusNotificacionIn(String uuid, List<EstadoNotificacion> estados);
}
