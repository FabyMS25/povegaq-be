package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Requisito;
import com.gamq.ambiente.model.TipoCombustible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoCombustibleRepository extends JpaRepository<TipoCombustible, Long> {
    Optional<TipoCombustible> findByUuid(String uuid);
    @Query("SELECT t FROM TipoCombustible t  WHERE LOWER(rtrim(ltrim(t.nombre))) = LOWER(rtrim(ltrim(:nombre)))")
    Optional<TipoCombustible> findByNombre(@Param("nombre") String nombre);
    @Query("SELECT case when count(t) > 0 then true else false end FROM TipoCombustible t WHERE lower(rtrim(ltrim(t.nombre))) = lower(rtrim(ltrim(:nombre))) AND t.uuid <> :uuidTipoCombustible")
    boolean exitsTipoCombustibleLikeNombre(@Param("nombre") String nombre,
                                           @Param("uuidTipoCombustible") String uuidTipoCombustible);
}
