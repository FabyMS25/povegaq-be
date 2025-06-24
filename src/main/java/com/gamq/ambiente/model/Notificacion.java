package com.gamq.ambiente.model;

import com.gamq.ambiente.enumeration.EstadoNotificacion;
import com.gamq.ambiente.enumeration.TipoNotificacion;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_notificacion", nullable = false, length = 30)
    private TipoNotificacion typeNotificacion; //     REINSPECCION PENDIENTE, INFRACCION, RECORDATORIO
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_asistencia", nullable = false)
    private Date fechaAsistencia;
    @Column(name = "hora_asistencia", nullable = false)
    private LocalTime horaAsistencia;
    @Column(name = "observacion", columnDefinition = "TEXT")
    private String observacion;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_notificacion", nullable = false)
    private Date fechaNotificacion;
    @Column(name = "nombre_notificador", nullable = false, length = 100)
    private String nombreNotificador;
    @Column(name = "uuid_usuario", nullable = false, length = 64)
    private String uuidUsuario;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_notificacion", length = 30)
    private EstadoNotificacion statusNotificacion; //ENVIADA, PENDIENTE, ENTREGADA, FALLIDA, VENCIDA
    @Column(name = "actividad", length = 200)
    private String actividad;
    @Column(name = "direccion", length = 250)
    private String direccion;
    @Column(name = "numero_intento", nullable = false)
    private int numeroIntento;
    @Column(name = "sancion", length = 250)
    private String sancion;
    @Column(name = "es_denuncia", nullable = true,columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean esDenuncia;
    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="id_inspeccion", nullable = false)
    private Inspeccion inspeccion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "notificacion", fetch = FetchType.LAZY)
    private List<Infraccion> infraccionList = new ArrayList<Infraccion>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "notificacion", fetch = FetchType.LAZY)
    private List<Alerta> alertaList = new ArrayList<Alerta>();

    public Notificacion(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        if (this.uuid == null || this.uuid.isEmpty()){
            this.setUuid(UUID.randomUUID().toString());
        }
    }
}
