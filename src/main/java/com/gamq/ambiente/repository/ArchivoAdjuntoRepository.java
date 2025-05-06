package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.ArchivoAdjunto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ArchivoAdjuntoRepository extends JpaRepository<ArchivoAdjunto, Long> {
    @Query("SELECT a FROM ArchivoAdjunto a WHERE a.uuid = ?1")
    Optional<ArchivoAdjunto> findByUuid(String uuid);
    @Query(value = "SELECT a.* FROM archivo_adjunto a WHERE a.uuid =?1",nativeQuery = true)
    Optional<ArchivoAdjunto> findByUuidArchivoAdjuntoActivoInactivo(String uuid);
    @Query("SELECT a FROM ArchivoAdjunto a WHERE a.nombre = ?1")
    Optional<ArchivoAdjunto> findByNombre(String nombre);
    @Query("SELECT a FROM ArchivoAdjunto a WHERE a.requisitoInspeccion.uuid = ?1 ORDER BY a.fechaAdjunto DESC")
    List<ArchivoAdjunto> findByUuidRequisitoInspeccion(String uuidRequisitoInspeccion);

    @Query(value = " select a.* from archivo_adjunto as a " +
            "inner join requisitos_inspeccion as r on r.id_requisito_inspeccion = a.id_requisito_inspeccion " +
            "where r.estado = false and r.uuid =?1 " +
            "order by a.fecha_adjunto DESC", nativeQuery = true)
    List<ArchivoAdjunto> findByUuidRequisitoInspeccionActivosYInactivos(String uuidRequisitoInspeccion);
}
