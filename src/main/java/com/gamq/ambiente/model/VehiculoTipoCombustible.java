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
@Table(name = "vehiculo_tipo_combustible", indexes = @Index(name = "idx_vtco", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE vehiculo_tipo_combustible SET estado=true WHERE id_vehiculo_tipo_combustible=?")
@Where(clause = "estado = false")
public class VehiculoTipoCombustible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo_tipo_combustible")
    private Long idVehiculoTipoCombustible;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "es_primario", nullable = false)
    private Boolean esPrimario;
    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_combustible", nullable = false)
    private TipoCombustible tipoCombustible;

    public VehiculoTipoCombustible(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.setUuid(UUID.randomUUID().toString());
        }
    }
}
