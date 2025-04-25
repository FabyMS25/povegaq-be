package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Certificado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CertificadoRepository extends JpaRepository<Certificado, Long> {
    Optional<Certificado> findByUuid(String uuid);
    Optional<Certificado> findByCodigo(String codigo);
}
