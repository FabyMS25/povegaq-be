package com.gamq.ambiente.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.gamq.ambiente.model.Vehiculo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class FotoVehiculoDto {
    private String uuid;
    private String nombre;
    private String ruta;
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    @Size(max = 100, message = "La direccion no puede superar 100 caracteres")
    private String nombreUsuario;
    @NotBlank(message = "El uuid de usuario no puede estar vacío")
    private String uuidUsuario;

    private boolean estado;

    @NotBlank(message = "El archivo no puede ser nulo")
    private MultipartFile archivoFile;

    private VehiculoDto vehiculoDto;
}
