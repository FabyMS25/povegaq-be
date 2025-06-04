package com.gamq.ambiente.repository;

import com.gamq.ambiente.enumeration.GradoInfraccion;
import com.gamq.ambiente.model.TipoContribuyente;
import com.gamq.ambiente.model.TipoInfraccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Repository
public interface TipoInfraccionRepository extends JpaRepository<TipoInfraccion, Long> {
    Optional<TipoInfraccion> findByUuid(String uuid);
    @Query("SELECT t FROM TipoInfraccion t  WHERE t.grado = :grado  AND t.reglamento.activo = true ")
    Optional<TipoInfraccion> findByGrado(@Param("grado") GradoInfraccion grado);

    @Query("SELECT t FROM TipoInfraccion t WHERE t.tipoContribuyente.uuid = :uuidTipoContribuyente AND  t.grado =  :grado AND t.reglamento.activo = true")
    Optional<TipoInfraccion> findTipoInfraccionByUuidTipoContribuyenteAndGrado(@Param("uuidTipoContribuyente") String uuidTipoContribuyente,
                                                                               @Param("grado") GradoInfraccion grado);

    @Query("SELECT (COUNT(t) > 0) FROM TipoInfraccion t WHERE t.reglamento.activo = true AND t.grado = :grado AND t.tipoContribuyente.uuid = :uuidTipoContribuyente AND t.uuid <> :uuidTipoInfraccion ")
    boolean existsTipoInfraccionLikeUuidTipoContribuyenteAndGrado(@Param("grado") GradoInfraccion grado,
                                                                  @Param("uuidTipoContribuyente") String uuidTipoContribuyente,
                                                                  @Param("uuidTipoInfraccion") String uuidTipoInfraccion);

   // @Query("SELECT t FROM TipoInfraccion t WHERE t.tipoContribuyente.uuid = :uuidTipoContribuyente AND  LOWER(t.grado) =  LOWER(:grado) AND t.reglamento.activo = true")
    Optional<TipoInfraccion> findByGradoAndTipoContribuyente(GradoInfraccion grado,
                                                             TipoContribuyente tipoContribuyente);
                                                          //   @Param("uuidTipoContribuyente") String uuidTipoContribuyente);
}
