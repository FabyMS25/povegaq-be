package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Ufv;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface UfvRepository extends JpaRepository<Ufv, Long> {
    Optional<Ufv> findByUuid(String uuid);
    @Query("SELECT u FROM Ufv u WHERE u.fecha = ?1")
    Optional<Ufv> findByFecha(Date fecha);
    List<Ufv> findAllByOrderByFechaDesc();
    @Query("SELECT u FROM Ufv u WHERE u.fecha BETWEEN ?1 AND ?2 ORDER BY u.fecha desc")
    List<Ufv> findListUfvByFecha(Date fechaIni, Date fechaFin);
    @Query("SELECT case when count(u) > 0 then true else false end FROM Ufv u WHERE FUNCTION('DATE', u.fecha) = :fecha AND u.uuid <> :uuidUfv")
    boolean exitsUfvLikeFecha(@Param("fecha") Date fecha,
                              @Param("uuidUfv") String uuidUfv);
}
