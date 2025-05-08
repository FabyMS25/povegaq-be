package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Conductor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConductorRepository  extends JpaRepository<Conductor, Long> {
    Optional<Conductor> findByUuid(String uuid);
    Optional<Conductor> findByNumeroDocumento(String numeroDocumento);
    @Query("SELECT case when count(c) > 0 then true else false end FROM Conductor c WHERE lower(rtrim(ltrim(c.numeroDocumento))) = lower(rtrim(ltrim(:numeroDocumento))) AND c.uuid <> :uuidConductor")
    boolean exitsConductorLikeNumeroDocumento(@Param("numeroDocumento") String numeroDocumento,
                                              @Param("uuidConductor") String uuidConductor);
}
