package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.Conductor;
import com.gamq.ambiente.model.DatoTecnico;
import com.gamq.ambiente.model.FotoVehiculo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VehiculoDto {
    private String uuid;
    private String placa;
    private String poliza;
    private String vinNumeroIdentificacion;
    private boolean esOficial;
    @NotNull(message = "La fecha de elaboracion es requerido YYYY-MM-DD")
    private Date fechaRegistro;
    private String juridiccionOrigen;
    @NotNull(message = "El campo es Movil es requerido TRUE o FALSE")
    private Boolean esMovil;
    private boolean esUnidadIndustrial;
    private String pinNumeroIdentificacion; //producto numero de identificacion unidad industrial
    private Integer nroCopiasPlaca;
    private String placaAnterior;
    private String chasis;
    private boolean estado;

    private DatoTecnicoDto datoTecnicoDto;
    private PropietarioDto propietarioDto;
    private ConductorDto conductorDto;
    private List<InspeccionDto> inspeccionDtoList = new ArrayList<InspeccionDto>();
    private List<FotoVehiculoDto> fotoVehiculoDtoList = new ArrayList<FotoVehiculoDto>();

}
