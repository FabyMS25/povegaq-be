package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Reglamento;
import com.gamq.ambiente.model.Requisito;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ReglamentoRepository extends JpaRepository<Reglamento, Long> {
    @Query("SELECT r FROM Reglamento r WHERE r.uuid = :uuid AND r.estado = false")
    Optional<Reglamento> findByUuid(String uuid);
    @Query("SELECT r FROM Reglamento r WHERE r.codigo = :codigo AND r.estado = false")
    Optional<Reglamento> findByCodigo(String codigo);
    @Query("SELECT r FROM Reglamento r WHERE r.activo = true")
    Optional<Reglamento> findActivoReglamento();
    @Transactional
    @Modifying
    @Query(value= "UPDATE reglamentos SET activo = false WHERE activo = true and estado = false ", nativeQuery = true)
    Integer updateAllToInactivo();
    @Query("SELECT case when count(r) > 0 then true else false end FROM Reglamento r WHERE r.activo = true AND lower(rtrim(ltrim(r.codigo))) = lower(rtrim(ltrim(:codigo))) AND r.uuid <> :uuidReglamento")
    boolean exitsReglamentoLikeCodigo(@Param("codigo") String codigo,
                                      @Param("uuidReglamento") String uuidReglamento);
}
