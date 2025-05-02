package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.Size;
import java.util.ArrayList;
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
        @Size(max = 250, message = "El nombre completo  no puede exceder los 250 caracteres")
        private String nombreCompleto;
        @Size(min= 4, max = 15)
        @Size(max = 15, message = "El nro de documento no puede exceder los 15 caracteres")
        private String nroDocumento;
        private Integer tipoDocumento;
        private Integer expedido;
        @Size(max = 50, message = "El correo no puede exceder los 50  caracteres")
        private String email;
        @Size(max = 15, message = "El numero de telefono no puede exceder los 15  caracteres")
        private String nroTelefono;
        private boolean estado;

        private TipoContribuyenteDto tipoContribuyenteDto;
        private List<VehiculoDto> vehiculoDtoList = new ArrayList<VehiculoDto>();


}
