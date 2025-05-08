package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
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
public class PropietarioDto {
        private String uuid;

        @Size(min= 1, max = 100)
        @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
        private String nombre;
        @Size(min= 1, max = 80)
        @Size(max = 80, message = "El apellido paterno no puede exceder los 80 caracteres")
        private String primerApellido;
        private String segundoApellido;
        private String apellidoEsposo;
        private String estadoCivil;
        private String genero;
        private Date fechaNacimiento;

        private String nombreCompleto;
        @Size(min= 4, max = 15)
        @Size(max = 15, message = "El nro de documento no puede exceder los 15 caracteres")
        private String numeroDocumento;
        private String tipoDocumento;
        private Integer expedido;
        @Size(max = 50, message = "El correo no puede exceder los 50  caracteres")
        private String email;
        @Size(max = 15, message = "El numero de telefono no puede exceder los 15  caracteres")
        private String nroTelefono;
        private boolean estado;

        private TipoContribuyenteDto tipoContribuyenteDto;
        private List<VehiculoDto> vehiculoDtoList = new ArrayList<VehiculoDto>();


}
