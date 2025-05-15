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
@Table(name = "inspecciones", indexes = @Index(name = "idx_insp", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE inspecciones SET estado=true WHERE id_inspeccion=?")
@Where(clause = "estado = false")
public class Inspeccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inspeccion")
    private Long idInspeccion;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_inspeccion", nullable = false)
    private Date fechaInspeccion;
    @Column(name = "resultado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean resultado;
    @Column(name = "observacion", length = 250)
    private String observacion;
    @Column(name = "lugar_inspeccion", length = 250)
    private String lugarInspeccion;

    @Column(name = "nombreInspector", nullable = false, length = 100)
    private String nombreInspector;
    @Column(name = "uuid_usuario")
    private String uuidUsuario;

    @Column(name = "equipo", nullable = false, length = 100)
    private String equipo;

    @Column(name = "altitud", nullable = true)
    private Integer altitud;

    @Column(name = "examen_visual_conforme", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean examenVisualConforme;
    @Column(name = "gases_escape_conforme", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean gasesEscapeConforme;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_proxima_inspeccion", nullable = true)
    private Date fechaProximaInspeccion;

    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_actividad", nullable = false)
    private Actividad actividad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_evento", nullable = true)  // Puede ser NULL
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_conductor", nullable = true) //verificar esto
    private Conductor conductor;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "inspeccion", fetch = FetchType.LAZY)
    private List<Certificado> certificadoList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "inspeccion", fetch = FetchType.LAZY)
    @OrderBy("numeroNotificacion ASC")
    private List<Notificacion> notificacionList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "inspeccion", fetch = FetchType.LAZY)
    private List<DetalleInspeccion> detalleInspeccionList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "inspeccion", fetch = FetchType.LAZY)
    private List<Infraccion> infraccionList = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "inspeccion", fetch = FetchType.LAZY)
    private List<RequisitoInspeccion> requisitoInspeccionList = new ArrayList<>();




    


    public Inspeccion(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        this.setUuid(UUID.randomUUID().toString());
    }
}
