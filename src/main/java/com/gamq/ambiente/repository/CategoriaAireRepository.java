package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.CategoriaAire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoriaAireRepository extends JpaRepository<CategoriaAire, Long> {
    Optional<CategoriaAire> findByUuid(String uuid);
}
