package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.MedicionAire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MedicionAireRepository extends JpaRepository<MedicionAire, Long> {
    Optional<MedicionAire> findByUuid(String uuid);
    @Query("SELECT m FROM MedicionAire m WHERE FUNCTION('DATE', m.fecha) =:fecha")
    List<MedicionAire> findByFecha(Date fecha);

}
