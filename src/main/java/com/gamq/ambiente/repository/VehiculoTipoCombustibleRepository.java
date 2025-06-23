package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.VehiculoTipoCombustible;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehiculoTipoCombustibleRepository extends JpaRepository<VehiculoTipoCombustible, Long> {
    Optional<VehiculoTipoCombustible> findByUuid(String uuid);
}
