package com.gamq.ambiente.repository;

import com.gamq.ambiente.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    Optional<Vehiculo> findByUuid(String uuid);
    @Query("SELECT v FROM Vehiculo v  WHERE LOWER(rtrim(ltrim(v.placa))) = LOWER(rtrim(ltrim(:placa)))")
    Optional<Vehiculo> findByPlaca(@Param("placa") String placa);
    @Query("SELECT v FROM Vehiculo v  WHERE LOWER(rtrim(ltrim(v.poliza))) = LOWER(rtrim(ltrim(:poliza)))")
    Optional<Vehiculo> findByPoliza(@Param("poliza") String poliza);
    @Query("SELECT v FROM Vehiculo v  WHERE LOWER(rtrim(ltrim(v.vinNumeroIdentificacion))) = LOWER(rtrim(ltrim(:vinNumeroIdentificacion)))")
    Optional<Vehiculo> findByVinNumeroIdentificacion(@Param("vinNumeroIdentificacion") String vinNumeroIdentificacion);
    @Query("SELECT v FROM Vehiculo v  WHERE LOWER(rtrim(ltrim(v.pinNumeroIdentificacion))) = LOWER(rtrim(ltrim(:pinNumeroIdentificacion)))")
    Optional<Vehiculo> findByPinNumeroIdentificacion(@Param("pinNumeroIdentificacion") String pinNumeroIdentificacion);
    @Query("SELECT v FROM Vehiculo v  WHERE LOWER(rtrim(ltrim(v.copo))) = LOWER(rtrim(ltrim(:copo)))")
    Optional<Vehiculo> findByCopo(@Param("copo") String copo);
    @Query("SELECT v FROM Vehiculo v  WHERE LOWER(rtrim(ltrim(v.placaAnterior))) = LOWER(rtrim(ltrim(:placaAnterior)))")
    Optional<Vehiculo> findByPlacaAnterior(@Param("placaAnterior") String placaAnterior);

    @Query("SELECT case when count(v) > 0 then true else false end FROM Vehiculo v WHERE lower(rtrim(ltrim(v.placa))) = lower(rtrim(ltrim(:placa))) AND v.uuid <> :uuid")
    boolean exitsVehiculoLikePlaca(@Param("placa") String placa,
                                   @Param("uuid") String uuid);
    @Query("SELECT case when count(v) > 0 then true else false end FROM Vehiculo v WHERE lower(rtrim(ltrim(v.poliza))) = lower(rtrim(ltrim(:poliza))) AND v.uuid <> :uuid")
    boolean exitsVehiculoLikePoliza(@Param("poliza") String poliza,
                                   @Param("uuid") String uuid);
    @Query("SELECT case when count(v) > 0 then true else false end FROM Vehiculo v WHERE lower(rtrim(ltrim(v.vinNumeroIdentificacion))) = lower(rtrim(ltrim(:vinNumeroIdentificacion))) AND v.uuid <> :uuid")
    boolean exitsVehiculoLikeVinNumeroIdentificacion(@Param("vinNumeroIdentificacion") String vinNumeroIdentificacion,
                                    @Param("uuid") String uuid);
    @Query("SELECT case when count(v) > 0 then true else false end FROM Vehiculo v WHERE lower(rtrim(ltrim(v.pinNumeroIdentificacion))) = lower(rtrim(ltrim(:pinNumeroIdentificacion))) AND v.uuid <> :uuid")
    boolean exitsVehiculoLikePinNumeroIdentificacion(@Param("pinNumeroIdentificacion") String pinNumeroIdentificacion,
                                                     @Param("uuid") String uuid);
}
