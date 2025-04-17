package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.TipoContribuyente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoContribuyenteRepository extends JpaRepository<TipoContribuyente, Long> {

    Optional<TipoContribuyente> findByUuid(String uuid);
    @Query("SELECT t FROM TipoContribuyente t  WHERE LOWER(t.descripcion) = LOWER(:descripcion)")
    Optional<TipoContribuyente> findByDescripcion(@Param("descripcion") String descripcion);
    @Query("SELECT case when count(t) > 0 then true else false end FROM TipoContribuyente t WHERE lower(t.descripcion) = lower(:descripcion) AND t.uuid <> :uuidTipoContribuyente")
    boolean exitsTipoContribuyenteLikeDescripcion(@Param("descripcion") String descripcion,
                                                  @Param("uuidTipoContribuyente") String uuidTipoContribuyente);
}
