package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.LimiteEmision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LimiteEmisionRepository extends JpaRepository<LimiteEmision, Long> {
    @Query("SELECT l FROM LimiteEmision l WHERE l.uuid = :uuid AND l.estado = false")
    Optional<LimiteEmision> findByUuid(String uuid);
    Optional<LimiteEmision> findByTipoCombustible(String tipoCombustible);
    @Query("SELECT l FROM LimiteEmision l WHERE l.activo = true AND l.tipoParametro.uuid = :uuidTipoParametro")
    List<LimiteEmision> findByUuidTipoParametro(@Param("uuidTipoParametro") String uuidTipoParametro);
    @Query("SELECT l FROM LimiteEmision l WHERE LOWER(l.tipoParametro.nombre) = ?1")
    Optional<LimiteEmision> findByNombreTipoParametro(String nombre);

    @Query("SELECT l FROM LimiteEmision l WHERE LOWER(l.tipoCombustible) = ?1 AND l.activo = true AND LOWER(l.tipoParametro.nombre) = ?2")
    Optional<LimiteEmision> findByTipoCombustibleAndNombreTipoParametro(String tipoCombustible, String nombre);

    @Query("SELECT case when count(l) > 0 then true else false end FROM LimiteEmision l WHERE lower(l.tipoCombustible) = lower(:tipoCombustible) AND l.activo = true AND lower(l.tipoParametro.nombre) = lower(:nombreTipoParametro)")
    boolean exitsLimiteEmisionLikeTipoConbustibleAndNombreTipoParametro(@Param("tipoCombustible") String tipoCombustible,
                                                                        @Param("nombreTipoParametro") String nombreTipoParametro);

    @Query("SELECT case when count(l) > 0 then true else false end FROM LimiteEmision l WHERE lower(l.tipoCombustible) = lower(:tipoCombustible) AND lower(l.tipoParametro.nombre) = lower(:nombreTipoParametro) AND l.activo = true AND l.uuid <> :uuidLimiteEmision")
    boolean exitsLimiteEmisionLikeNombreTipoParametro(@Param("tipoCombustible") String tipoCombustible,
                                                      @Param("nombreTipoParametro") String nombreTipoParametro,
                                                      @Param("uuidLimiteEmision") String uuidLimiteEmision);
    @Query("SELECT l FROM LimiteEmision l " +
            "WHERE l.tipoParametro.nombre = :tipoParametro " +
            "AND l.tipoCombustible = :tipoCombustible " +
            "AND l.tipoMotor = :tipoMotor " +
           // "AND l.tipoControl = :tipoControl " +
            "AND :yearFabricacion BETWEEN l.yearFabricacionInicio AND l.yearFabricacionFin " +
            "AND :altitud BETWEEN l.altitudMinima AND l.altitudMaxima")
    Optional<LimiteEmision> findByParametros(
            @Param("tipoParametro") String tipoParametro,
            @Param("tipoCombustible") String tipoCombustible,
            @Param("tipoMotor") String tipoMotor,
          //  @Param("tipoControl") String tipoControl,
            @Param("yearFabricacion") Integer yearFabricacion,
            @Param("altitud") Integer altitud
    );
}
