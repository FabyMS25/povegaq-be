package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.RequisitoInspeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequisitoInspeccionRepository extends JpaRepository<RequisitoInspeccion, Long> {
    Optional<RequisitoInspeccion> findByUuid(String uuid);
    @Query("SELECT r FROM RequisitoInspeccion r WHERE r.inspeccion.uuid = :uuidInspeccion ")
    List<RequisitoInspeccion> findByUuidInspeccion(@Param("uuidInspeccion") String uuidInspeccion);
    @Query("SELECT case when count(r) > 0 then true else false end FROM RequisitoInspeccion r WHERE r.inspeccion.uuid = :uuidInspeccion AND r.requisito.uuid = :uuidRequisito")
    boolean exitsByUuidInspeccionAndUuidRequisito(@Param("uuidInspeccion") String uuidInspeccion,
                                                   @Param("uuidRequisito") String uuidRequisito);

    @Query("SELECT case when count(r) > 0 then true else false end FROM RequisitoInspeccion r WHERE r.inspeccion.uuid = :uuidInspeccion AND r.requisito.uuid = :uuidRequisito AND r.uuid <> :uuidRequisitoInspeccion")
    boolean exitsLikeUuidRequisitoAndUuidInspeccion(@Param("uuidInspeccion") String uuidInspeccion,
                                                   @Param("uuidRequisito") String uuidRequisito,
                                                   @Param("uuidRequisitoInspeccion") String uuidRequisitoInspeccion);
}
