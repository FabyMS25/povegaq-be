package com.gamq.ambiente.repository;

import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.enumeration.TipoNotificacion;
import com.gamq.ambiente.model.Notificacion;
import com.gamq.ambiente.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    Optional<Notificacion> findByUuid(String uuid);
    Optional<Notificacion> findByNumeroNotificacion(String numeroNotificacion);
    int countByInspeccion_VehiculoAndStatusNotificacionIn(Vehiculo vehiculo, List<EstadoNotificacion> estados);
    @Query("SELECT case when count(n) > 0 then true else false end FROM Notificacion n WHERE lower(n.numeroNotificacion) = lower(:numeroNotificacion) AND n.uuid <> :uuidNotificacion")
    boolean exitsNotificacionLikeNumeroNotificacion(@Param("numeroNotificacion") String numeroNotificacion,
                                                    @Param("uuidNotificacion") String uuidNotificacion);
    List<Notificacion> findByTypeNotificacion(TipoNotificacion typeNotificacion);

    boolean existsByInspeccion_VehiculoAndTypeNotificacionAndStatusNotificacionIn(
            Vehiculo vehiculo,
            TipoNotificacion tipo,
            List<EstadoNotificacion> estados
    );

    @Query("SELECT n FROM Notificacion n WHERE (n.fechaAsistencia < CURRENT_DATE) and n.statusNotificacion NOT IN :estadoNotificacionList")
    List<Notificacion> findNotificacionesByFechaAsistenciaVencida(List<EstadoNotificacion> estadoNotificacionList);

    boolean existsByInspeccion_VehiculoAndTypeNotificacionAndFechaAsistenciaGreaterThanEqual(
            Vehiculo vehiculo,
            TipoNotificacion tipo,
            Date fecha);

    List<Notificacion> findByTypeNotificacionAndStatusNotificacionInAndFechaAsistenciaBefore(
            TipoNotificacion tipoNotificacion,
            List<EstadoNotificacion> estadosNotificacion,
            Date fechaActual
    );
}
