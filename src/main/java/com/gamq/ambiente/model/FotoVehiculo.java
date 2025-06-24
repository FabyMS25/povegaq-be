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
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name="fotos_vehiculo", indexes= @Index(name = "idx_fove_uuid", columnList ="uuid", unique= true ))
@SQLDelete(sql = "UPDATE fotos_vehiculo SET estado=true WHERE id_foto_vehiculo=?")
@Where(clause = "estado = false")
public class FotoVehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_foto_vehiculo")
    private Long idFotoVehiculo;
    @Column(name="uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "nombre", nullable = false, columnDefinition = "TEXT")
    private String nombre;
    @Column(name="ruta", nullable = false, columnDefinition = "TEXT")
    private String ruta;
    @Column(name = "nombreUsuario", nullable = false, length = 100)
    private String nombreUsuario;
    @Column(name = "uuid_usuario")
    private String uuidUsuario;
    @Column(name = "estado",  columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @Transient
    private MultipartFile archivoFile;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    public FotoVehiculo(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializaUuid() {
        if (this.uuid == null || this.uuid.isEmpty()){
            this.setUuid(UUID.randomUUID().toString());
        }
    }
}
