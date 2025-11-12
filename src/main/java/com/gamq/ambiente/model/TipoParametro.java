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
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "tipo_parametros", indexes = @Index(name = "idx_tipa", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE tipo_parametros SET estado=true WHERE id_tipo_parametro=?")
@Where(clause = "estado = false")
public class TipoParametro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_parametro")
    private Long idTipoParametro;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "nombre", unique = false, nullable = false, length = 100)
    private String nombre;
    @Column(name = "descripcion", nullable = true, length = 250)
    private String descripcion;
    @Column(name = "unidad", nullable = true, length = 50)
    private String unidad;
    @Column(name = "activo", columnDefinition = "BOOLEAN NOT NULL DEFAULT '1'")
    private boolean activo;

    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "tipoParametro", fetch = FetchType.LAZY)
    private List<LimiteEmision> limiteEmisionList = new ArrayList<LimiteEmision>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "tipoParametro", fetch = FetchType.LAZY)
    private List<DetalleInspeccion> detalleInspeccionList = new ArrayList<DetalleInspeccion>();

    public TipoParametro(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.setUuid(UUID.randomUUID().toString());
        }
    }
}
