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
import java.util.List;
import java.util.Optional;

@Repository
public interface TipoInfraccionRepository extends JpaRepository<TipoInfraccion, Long> {
    Optional<TipoInfraccion> findByUuid(String uuid);
    @Query("SELECT t FROM TipoInfraccion t  WHERE t.grado = :grado  AND t.reglamento.activo = true ")
    List<TipoInfraccion> findByGrado(@Param("grado") GradoInfraccion grado);

    @Query("SELECT t FROM TipoInfraccion t WHERE t.tipoContribuyente.uuid = :uuidTipoContribuyente AND  t.grado =  :grado AND LOWER(rtrim(ltrim(t.descripcion))) = LOWER(rtrim(ltrim(:descripcion))) AND t.reglamento.activo = true")
    Optional<TipoInfraccion> findTipoInfraccionByUuidTipoContribuyenteAndGradoAndDescripcion(@Param("uuidTipoContribuyente") String uuidTipoContribuyente,
                                                                               @Param("grado") GradoInfraccion grado,
                                                                               @Param("descripcion") String descripcion);

    @Query("SELECT t FROM TipoInfraccion t WHERE t.tipoContribuyente.uuid = :uuidTipoContribuyente AND  t.grado =  :grado AND t.reglamento.activo = true")
    Optional<TipoInfraccion> findTipoInfraccionByUuidTipoContribuyenteAndGrado(@Param("uuidTipoContribuyente") String uuidTipoContribuyente,
                                                                               @Param("grado") GradoInfraccion grado);

    @Query("SELECT (COUNT(t) > 0) FROM TipoInfraccion t WHERE t.reglamento.activo = true AND t.grado = :grado AND LOWER(rtrim(ltrim(t.descripcion))) = LOWER(rtrim(ltrim(:descripcion))) AND t.tipoContribuyente.uuid = :uuidTipoContribuyente AND t.uuid <> :uuidTipoInfraccion ")
    boolean existsTipoInfraccionLikeUuidTipoContribuyenteAndGrado(@Param("grado") GradoInfraccion grado,
                                                                  @Param("uuidTipoContribuyente") String uuidTipoContribuyente,
                                                                  @Param("descripcion") String descripcion,
                                                                  @Param("uuidTipoInfraccion") String uuidTipoInfraccion);

    Optional<TipoInfraccion> findByGradoAndTipoContribuyente(GradoInfraccion grado,
                                                             TipoContribuyente tipoContribuyente);

    @Query("SELECT t FROM TipoInfraccion t  WHERE t.tipoContribuyente.uuid = :uuidTipoConttribuyente AND t.esAutomatico = false AND t.reglamento.activo = true ")
    List<TipoInfraccion> findByEsAutomaticoFalseAndUuidTipoContribuyente(@Param("uuidTipoConttribuyente") String uuidTipoConttribuyente);

    @Query("SELECT t FROM TipoInfraccion t  WHERE LOWER(rtrim(ltrim(t.descripcion))) = LOWER(rtrim(ltrim(:descripcion))) AND t.grado = :grado AND t.tipoContribuyente = :tipoContribuyente AND t.esAutomatico = true")
    Optional<TipoInfraccion> findByDescripcionAndGradoInfraccionAndTipoContribuyente(String descripcion, GradoInfraccion grado, TipoContribuyente tipoContribuyente);

    @Query("SELECT t FROM TipoInfraccion t  WHERE LOWER(rtrim(ltrim(t.articulo))) = LOWER(rtrim(ltrim(:articulo))) AND t.grado = :grado AND t.tipoContribuyente = :tipoContribuyente AND t.esAutomatico = true")
    Optional<TipoInfraccion> findByArticuloAndGradoInfraccionAndTipoContribuyente(String articulo, GradoInfraccion grado, TipoContribuyente tipoContribuyente);
}
