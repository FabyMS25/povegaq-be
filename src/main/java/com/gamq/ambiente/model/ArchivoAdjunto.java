package com.gamq.ambiente.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name="archivo_adjunto", indexes= @Index(name = "idx_arad_uuid", columnList ="uuid", unique= true ))
@SQLDelete(sql = "UPDATE archivo_adjunto SET estado=true WHERE id_archivo_adjunto=?")
@Where(clause = "estado = false")
public class ArchivoAdjunto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_archivo_adjunto")
    private Long idArchivoAdjunto;
    @Column(name="uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "nombre", nullable = false, columnDefinition = "TEXT")
    private String nombre;
     @Column(name="descripcion",nullable = true, length = 250)
    private String descripcion;
    @Column(name = "ruta_archivo", nullable = false, columnDefinition = "TEXT")
    private String rutaArchivo;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_adjunto")
    private Date fechaAdjunto;
    @Column(name = "nombreUsuario", nullable = false, length = 100)
    private String nombreUsuario;
    @Column(name = "uuid_usuario")
    private String uuidUsuario;
    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @Transient
    private MultipartFile archivoFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_requisito_inspeccion", nullable = false)
    private RequisitoInspeccion requisitoInspeccion;

    public ArchivoAdjunto(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.setUuid(UUID.randomUUID().toString());
        }
    }
}
