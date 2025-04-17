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
@Table(name = "eventos", indexes = @Index(name = "idx_even", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE eventos SET estado=true WHERE id_evento=?")
@Where(clause = "estado = false")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    private Long idEvento;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "institucion", nullable = false, length = 250)
    private String institucion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio")
    private Date fechaInicio;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_fin")
    private Date fechaFin;

    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_actividad", nullable = true)
    private Actividad actividad;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "evento", fetch = FetchType.LAZY)
    private List<Inspeccion> inspeccionList = new ArrayList<Inspeccion>();

    public Evento(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        this.setUuid(UUID.randomUUID().toString());
    }
}
