package com.gamq.ambiente.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "clase_vehiculo", indexes = @Index(name = "idx_date", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE clase_vehiculo SET estado=true WHERE id_clase_vehiculo=?")
@Where(clause = "estado = false")
public class ClaseVehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clase_vehiculo")
    private Long idClaseVehiculo;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "nombre", nullable = false, unique = true, length = 50)
    private String nombre;
    @Column(name = "descripcion", length = 150)
    private String descripcion;
    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "claseVehiculo", fetch = FetchType.LAZY)
    private List<TipoClaseVehiculo> tipoClaseVehiculoList = new ArrayList<>();

    public ClaseVehiculo(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.setUuid(UUID.randomUUID().toString());
        }
    }
}
