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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min= 1, max = 100)
        @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres")
        private String nombre;
        @NotBlank(message = "El apellido paterno es obligatorio")
        @Size(min= 1, max = 80)
        @Size(max = 80, message = "El apellido paterno no puede exceder los 80 caracteres")
        private String primerApellido;
        @Size(max = 80, message = "El segundo apellido no puede exceder los 80 caracteres")
        private String segundoApellido;
        @Size(max = 80, message = "El apellido esposo no puede exceder los 80 caracteres")
        private String apellidoEsposo;
        @Size(max = 15, message = "El estado civil no puede exceder los 15 caracteres")
        private String estadoCivil;
        @Size(max = 3, message = "El genero no puede exceder los 3 caracteres")
        private String genero;
        private Date fechaNacimiento;

        private String nombreCompleto;
        @NotBlank(message = "El número de documento es obligatorio")
        @Size(min= 4, max = 15)
        @Size(max = 15, message = "El nro de documento no puede exceder los 15 caracteres")
        private String numeroDocumento;
        @NotBlank(message = "El tipo de documento es obligatorio")
        private String tipoDocumento;
        private Integer expedido;
        @Email(message = "El correo electrónico no tiene un formato válido")
        @Size(max = 50, message = "El correo no puede exceder los 50  caracteres")
        private String email;
        @Size(max = 15, message = "El numero de telefono no puede exceder los 15  caracteres")
        private String nroTelefono;
        private boolean estado;

        @NotNull(message = "El tipo de contribuyente es obligatorio.")
        private TipoContribuyenteDto tipoContribuyenteDto;
        private List<VehiculoDto> vehiculoDtoList = new ArrayList<VehiculoDto>();
}
