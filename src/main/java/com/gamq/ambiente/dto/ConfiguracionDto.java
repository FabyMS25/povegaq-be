package com.gamq.ambiente.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfiguracionDto {
    private String uuid;
    @NotBlank(message = "La clave no puede ser nula ni vacía")
    @Size(max = 100, message = "La clave no puede exceder los 100 caracteres")
    private String clave;
    @NotBlank(message = "El valor no puede ser nula ni vacía")
    private String valor;
    private String unidad;
    private String descripcion;
    private Date fechaInicio;
    private Date fechaFin;
    private String resolucionApoyo;
    @NotBlank(message = "El nombre del registrador es requerido")
    @Size(max = 100, message = "El nombre del registrador no puede exceder los 100 caracteres")
    private String registradoPor;
    @NotBlank(message = "El uuid del usuario es requerido")
    @Size(max = 64, message = "El uuidUsuario no puede exceder los 64 caracteres")
    private String uuidUsuario;
    private Date fechaRegistro;
    private boolean activo;
    private boolean estado;
}
