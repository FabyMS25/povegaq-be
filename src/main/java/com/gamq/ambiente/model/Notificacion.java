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
@Table(name = "notificaciones", indexes = @Index(name = "idx_noti", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE notificaciones SET estado=true WHERE id_notificacion=?")
@Where(clause = "estado = false")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Long idNotificacion;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "numero_notificacion", nullable = false, length = 15)
    private String numeroNotificacion;
    @Column(name = "tipo_notificacion", nullable = false, length = 50)
    private String tipoNotificacion;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_limite", nullable = false)
    private Date fechaLimite;
    @Column(name = "vencido", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean vencido;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_asistencia", nullable = false)
    private Date fechaAsistencia;
    @Column(name = "hora_asistencia", nullable = false)
    private LocalTime horaAsistencia;


    @Column(name = "observacion", length = 250)
    private String observacion;
    @Column(name = "status_notificacion", length = 30) //
    private String statusNotificacion;
    @Column(name = "recordatorio", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean recordatorio;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_notificacion", nullable = false)
    private Date fechaNotificacion;
    @Column(name = "nombre_notificador", nullable = false, length = 100)
    private String nombreNotificador;
    @Column(name = "uuid_usuario")
    private String uuidUsuario;
    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="id_inspeccion", nullable = false)
    private Inspeccion inspeccion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "notificacion", fetch = FetchType.LAZY)
    private List<Infraccion> infraccionList = new ArrayList<Infraccion>();

    public Notificacion(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        this.setUuid(UUID.randomUUID().toString());
    }
}
