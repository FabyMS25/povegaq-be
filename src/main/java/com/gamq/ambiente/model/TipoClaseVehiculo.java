package com.gamq.ambiente.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "tipo_clase_vehiculo", indexes = @Index(name = "idx_tpcv", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE tipo_clase_vehiculo SET estado=true WHERE id_tipo_clase_vehiculo=?")
@Where(clause = "estado = false")
public class TipoClaseVehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_clase_vehiculo")
    private Long idTipoClaseVehiculo;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String nombre;
    @Column(name = "descripcion", length = 150)
    private String descripcion;
    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_clase_vehiculo", nullable = false)
    private ClaseVehiculo claseVehiculo;

    public TipoClaseVehiculo(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.setUuid(UUID.randomUUID().toString());
        }
    }
}
