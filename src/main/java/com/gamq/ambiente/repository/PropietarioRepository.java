package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PropietarioRepository extends JpaRepository<Propietario, Long> {
    Optional<Propietario> findByUuid(String uuid);
    Optional<Propietario> findByEmail(String email);
    Boolean existsByEmail(String email);
    @Query("SELECT p FROM Propietario p  WHERE LOWER(p.numeroDocumento) =?1")
    Optional<Propietario> findByNumeroDocumento(String numeroDocumento);
    @Query("SELECT case when count(p) > 0 then true else false end FROM Propietario p WHERE lower(p.numeroDocumento) = lower(:numeroDocumento) AND p.uuid <> :uuidPropietario")
    boolean exitsPropietarioLikeNroDocumento(@Param("numeroDocumento") String numeroDocumento,
                                             @Param("uuidPropietario") String uuidPropietario);
}
