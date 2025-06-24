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
@Table(name = "tipo_combustibles", indexes = @Index(name = "idx_tico", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE tipo_combustibles SET estado=true WHERE id_tipo_combustible=?")
@Where(clause = "estado = false")
public class TipoCombustible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_combustible")
    private Long idTipoCombustible;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "nombre", unique = false, nullable = false, length = 50)
    private String nombre;
    @Column(name = "descripcion", nullable = true, length = 250)
    private String descripcion;
    @Column(name = "tipo_motor", nullable = false, length = 50)
    private String tipoMotor;

    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @OneToMany( cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "tipoCombustible")  // true elimina el hijo
    private List<VehiculoTipoCombustible> vehiculoTipoCombustibleList = new ArrayList<>();


    public TipoCombustible(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.setUuid(UUID.randomUUID().toString());
        }
    }
}
