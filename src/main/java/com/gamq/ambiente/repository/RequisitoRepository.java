package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Requisito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequisitoRepository extends JpaRepository<Requisito, Long> {
    Optional<Requisito> findByUuid(String uuid);
    @Query("SELECT r FROM Requisito r  WHERE LOWER(rtrim(ltrim(r.descripcion))) = LOWER(rtrim(ltrim(:descripcion)))")
    Optional<Requisito> findByDescripcion(@Param("descripcion") String descripcion);
    @Query("SELECT case when count(r) > 0 then true else false end FROM Requisito r WHERE lower(rtrim(ltrim(r.descripcion))) = lower(rtrim(ltrim(:descripcion))) AND r.uuid <> :uuidRequisito")
    boolean exitsRequisitoLikeDescripcion(@Param("descripcion") String descripcion,
                                          @Param("uuidRequisito") String uuidRequisito);
}
