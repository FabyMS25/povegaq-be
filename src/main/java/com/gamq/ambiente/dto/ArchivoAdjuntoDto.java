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
    private String descripcion;
    private String rutaArchivo;
    private Date fechaAdjunto;
    private String nombreUsuario;
    private String uuidUsuario;
    private boolean estado;

    private MultipartFile archivoFile;

    private RequisitoInspeccionDto requisitoInspeccionDto;

}
