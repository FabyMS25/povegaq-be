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
@Table(name = "reglamentos", indexes = @Index(name = "idx_regl", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE reglamentos SET estado=true WHERE id_reglamento=?")
@Where(clause = "estado = false")
public class Reglamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reglamento")
    private Long idReglamento;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name="codigo", nullable = false, length = 15)
    private String codigo;
    @Column(name = "descripcion", nullable = false, length = 250)
    private String descripcion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_emision", nullable = false)
    private Date fechaEmision;
    @Column(name = "activo", columnDefinition = "BOOLEAN NOT NULL DEFAULT '1'")
    private boolean activo;

    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "reglamento", fetch = FetchType.LAZY)
    private List<TipoInfraccion> tipoInfraccionList = new ArrayList<TipoInfraccion>();


    public Reglamento(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.setUuid(UUID.randomUUID().toString());
        }
    }
}
