package com.gamq.ambiente.repository;

import com.gamq.ambiente.dto.NotificacionIntentoDto;
import com.gamq.ambiente.dto.NotificacionIntentoView;
import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.enumeration.TipoNotificacion;
import com.gamq.ambiente.model.Notificacion;
import com.gamq.ambiente.model.Vehiculo;
import com.gamq.ambiente.serviceimplement.NotificacionServiceImpl;
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

    @Query(value=" SELECT v.uuid as uuidVehiculo, "+
            "    COALESCE(MAX(n.numero_intento), 0) AS intentosValidos, "+
            "    CASE "+
            "        WHEN MAX(n.numero_intento) IS NULL OR MAX(n.numero_intento) = 0 THEN 'REINSPECCION_PENDIENTE' "+
            "        WHEN MAX(n.numero_intento)= 1 THEN 'INFRACCION' "+
            "		WHEN MAX(n.numero_intento)= 2 THEN 'INFRACCION_FINAL' "+
            "		ELSE 'RECORDATORIO' "+
            "    END AS proximoTipoNotificacion, "+
            "	    CASE "+
            "        WHEN MAX(n.numero_intento) IS NULL OR MAX(n.numero_intento) = 0 THEN true "+
            "        WHEN MAX(n.numero_intento)= 1 THEN true "+
            "		WHEN MAX(n.numero_intento)= 2 THEN true "+
            "		ELSE false "+
            "    END AS puedeEmitirNuevaNotificacion "+
            " FROM inspecciones i INNER JOIN vehiculos v ON v.id_vehiculo = i.id_vehiculo "+
            " LEFT JOIN notificaciones n ON i.id_inspeccion = n.id_inspeccion and n.estado = false "+
            " WHERE v.uuid =:uuidVehiculo "+
            "  AND v.estado = false AND i.estado = false AND i.resultado = false "+
            "  AND (n.id_inspeccion IS NULL OR n.status_notificacion = 'VENCIDA') "+
            "  AND NOT EXISTS ( "+
            "      SELECT 1 "+
            "      FROM notificaciones n2 "+
            "      WHERE n2.id_inspeccion = n.id_inspeccion "+
            "        AND n2.status_notificacion = 'CUMPLIDA' "+
            "        AND n2.numero_intento > n.numero_intento "+
            "        AND n2.estado = false) "+
            "  AND NOT EXISTS ( "+
            "      SELECT 1 "+
            "      FROM inspecciones i2 "+
            "      WHERE i2.id_vehiculo = v.id_vehiculo "+
            "        AND i2.resultado = true "+
            "        AND i2.fecha_inspeccion > i.fecha_inspeccion "+
            "        AND i2.estado = false) "+
            "  AND NOT EXISTS ( "+
            "      SELECT 1 "+
            "      FROM infracciones inf "+
            "      WHERE inf.id_inspeccion = i.id_inspeccion "+
            "        AND inf.status_infraccion IN ('PAGADA', 'CANCELADA') "+
            "        AND inf.estado = false) "+
            "  GROUP BY v.uuid "
            , nativeQuery = true)
    Optional<NotificacionIntentoView> getNumeroIntentoNotificacionByUuidVehiculo(@Param("uuidVehiculo") String uuidVehiculo);

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

    Optional<Notificacion> findFirstByPlacaOrderByFechaNotificacionDesc(String placa);
}
