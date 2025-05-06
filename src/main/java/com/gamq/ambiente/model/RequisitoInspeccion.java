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
@Table(name = "requisitos_inspeccion", indexes = @Index(name = "idx_requ", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE requisitos_inspeccion SET estado=true WHERE id_requisito_inspeccion=?")
@Where(clause = "estado = false")
public class RequisitoInspeccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_requisito_inspeccion")
    private Long idRequisitoInspeccion;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "cumple", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean cumple;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_presentacion", nullable = false)
    private Date fechaPresentacion;
    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_requisito", nullable = false)
    private Requisito requisito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inspeccion", nullable = false)
    private Inspeccion inspeccion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "requisitoInspeccion", fetch = FetchType.LAZY)
    private List<ArchivoAdjunto> archivoAdjuntoList = new ArrayList<ArchivoAdjunto>();

    public RequisitoInspeccion(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        this.setUuid(UUID.randomUUID().toString());
    }
}
