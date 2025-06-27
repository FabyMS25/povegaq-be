package com.gamq.ambiente.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "configuraciones", indexes = @Index(name = "idx_cofs", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE configuraciones SET estado=true WHERE id_configuracion=?")
@Where(clause = "estado = false")
public class Configuracion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_configuracion")
    private Long idConfiguracion;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name="clave",  nullable = false, length = 100)
    private String clave;
    @Column(name = "valor", nullable = false)
    private String valor;
    @Column(name ="unidad", length = 50)
    private String unidad;
    @Column(name = "descripcion", length = 255)
    private String descripcion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio")
    private Date fechaInicio;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_fin")
    private Date fechaFin;
    @Column(name = "resolucion_apoyo", length = 500)
    private String resolucionApoyo;
    @Column(name = "registrado_por", nullable = false, length = 100)
    private String registradoPor;
    @Column(name = "uuid_usuario", nullable = false, length = 64)
    private String uuidUsuario;
    @Column(name = "fecha_registro", nullable = false)
    private Date fechaRegistro;
    @Column(name = "activo", nullable = false, columnDefinition = "BOOLEAN NOT NULL DEFAULT '1'")
    private boolean activo;

    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    public Configuracion(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.setUuid(UUID.randomUUID().toString());
        }
    }
}
