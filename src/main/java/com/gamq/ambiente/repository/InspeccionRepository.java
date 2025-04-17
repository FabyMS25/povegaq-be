package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Inspeccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InspeccionRepository extends JpaRepository<Inspeccion, Long> {
    Optional<Inspeccion> findByUuid(String uuid);
    List<Inspeccion> findByUuidUsuario(String uuidUsuario);
    @Query("SELECT i FROM Inspeccion i WHERE LOWER(rtrim(ltrim(i.vehiculo.placa))) = LOWER(rtrim(ltrim(:placa)))")
    List<Inspeccion> findByPlacaVehiculo(@Param("placa") String placa);
}
