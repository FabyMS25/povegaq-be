package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface InfraccionRepository extends JpaRepository<Infraccion, Long> {
    Optional<Infraccion> findByUuid(String uuid);
    @Query("SELECT i FROM Infraccion i WHERE FUNCTION('DATE', i.fechaInfraccion) = :fechaInfraccion")
    List<Infraccion> findByFechaInfraccion(@Param("fechaInfraccion") Date fechaInfraccion);

    List<Infraccion> findByInspeccionVehiculo(Vehiculo vehiculo);
    @Query("SELECT i FROM Infraccion i WHERE i.vehiculo = :vehiculo")
    List<Infraccion> findByVehiculo(@Param("vehiculo") Vehiculo vehiculo);

    //2025
    //boolean existsByInspeccionUuidAndTipoInfraccionUuid(String uuidInspeccion, String uuidTipoInfraccion);
    //boolean existsByInspeccionAndTipoInfraccionAndEstadoPagoFalse(Inspeccion inspeccion, TipoInfraccion tipoInfraccion);
    //boolean existsByNotificacionAndTipoInfraccionAndEstadoPagoFalse(Notificacion notificacion, TipoInfraccion tipoInfraccion);
}
