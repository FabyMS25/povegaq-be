package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Inspeccion;
import com.gamq.ambiente.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface InspeccionRepository extends JpaRepository<Inspeccion, Long> {
    Optional<Inspeccion> findByUuid(String uuid);
    Optional<Inspeccion> findByCodigo(String codigo);
    List<Inspeccion> findByUuidUsuario(String uuidUsuario);
    @Query("SELECT i FROM Inspeccion i WHERE LOWER(rtrim(ltrim(i.vehiculo.placa))) = LOWER(rtrim(ltrim(:placa)))")
    List<Inspeccion> findByPlacaVehiculo(@Param("placa") String placa);
    @Query("SELECT i FROM Inspeccion i WHERE i.actividad.uuid = :uuidTipoActividad")
    List<Inspeccion> findByTipoActividad(@Param("uuidTipoActividad") String uuidTipoActividad);
    @Query("SELECT i FROM Inspeccion i WHERE FUNCTION('DATE', i.fechaInspeccion) = :fechaInspeccion ")
    List<Inspeccion> findByFechaInspeccion(@Param("fechaInspeccion") Date fechaInspeccion);

    List<Inspeccion> findByVehiculoAndResultadoFalseOrderByFechaInspeccionDesc(Vehiculo vehiculo);

    List<Inspeccion> findByResultadoFalse();

    Optional<Inspeccion> findFirstByVehiculoUuidOrderByFechaInspeccionDesc(String uuidVehiculo);

    @Query("SELECT case when count(i) > 0 then true else false end FROM Inspeccion i WHERE i.equipo.uuid = :uuidEquipo")
    boolean exitsInspeccionWithUuidEquipo(@Param("uuidEquipo") String uuidEquipo);

    List<Inspeccion> findByVehiculoAndEstado(Vehiculo vehiculo, boolean estado);

    @Query(value = "SELECT codigo FROM inspecciones WHERE codigo LIKE :prefijoAnio ORDER BY codigo DESC LIMIT 1",
            nativeQuery = true
    )
 //   @Query("SELECT i.codigo FROM Inspeccion i " +
 //           "WHERE i.codigo LIKE :prefijoAnio% " +
 //           "ORDER BY i.codigo DESC")
    Optional<String> findUltimoCodigoPorPrefijo(@Param("prefijoAnio") String prefijoAnio);
}
