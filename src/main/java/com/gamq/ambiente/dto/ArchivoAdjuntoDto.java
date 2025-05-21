package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ArchivoAdjuntoDto {
    private String uuid;
    private String nombre;
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 250, message = "La descripcion no puede exceder los 250 caracteres")
    private String descripcion;
    private String rutaArchivo;
    @NotNull(message = "La fecha del adjunto es obligatoria")
    private Date fechaAdjunto;
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(max = 100, message = "El nombre de usuario no puede tener más de 100 caracteres")
    private String nombreUsuario;
    @NotBlank(message = "El UUID del usuario es obligatorio")
    private String uuidUsuario;
    private boolean estado;

    private MultipartFile archivoFile;

    private RequisitoInspeccionDto requisitoInspeccionDto;

}
