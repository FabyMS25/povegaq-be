package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.LimiteEmision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LimiteEmisionRepository extends JpaRepository<LimiteEmision, Long> {
    Optional<LimiteEmision> findByUuid(String uuid);
    Optional<LimiteEmision> findByTipoCombustible(String tipoCombustible);
    @Query("SELECT l FROM LimiteEmision l WHERE l.tipoParametro.uuid = ?1")
    Optional<LimiteEmision> findByUuidTipoParametro(String uuid);
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
}
