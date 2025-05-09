package com.gamq.ambiente.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalTime;
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
    @Column(name = "institucion", nullable = true, length = 250)
    private String institucion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio")
    private Date fechaInicio;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_fin")
    private Date fechaFin;
    @Column(name = "hora_inicio")
    private LocalTime horaInicio;
    @Column(name = "hora_fin")
    private LocalTime horaFin;
    @Column(name = "latitud")
    private Double latitud;
    @Column(name="longitud")
    private Double longitud;
    @Column(name = "direccion", length = 250)
    private String direccion;
    @Column(columnDefinition = "TEXT", name = "descripcion", nullable = true)
    private String descripcion;
    @Column(name = "distrito", nullable = true, length = 15)
    private String distrito;
    @Column(name = "altitud", nullable = true)
    private Integer altitud;
    @Column(name = "titulo", length = 50)
    private String titulo;

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
