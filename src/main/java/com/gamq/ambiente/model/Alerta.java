package com.gamq.ambiente.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "alertas", indexes = @Index(name = "idx_aler", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE alertas SET estado=true WHERE id_alerta=?")
@Where(clause = "estado = false")
public class Alerta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alerta")
    private Long idAlerta;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name ="tipo_alerta", nullable = false, length = 50)
    private String tipo; //VENCIMIENTO, ALERTA NOTIFICACION de notificacion, ALERTA INFRACION de pago de multa infraccion,
    @Column(name = "mensaje", nullable = false, length = 250)
    private String mensaje;
    @Column(name = "fecha_alerta",nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAlerta;
    @Column(name="uuid_destinatario", nullable = true, length = 64)
    private String uuidDestinatario;
    @Column(name="rol_destinatario", nullable = true, length= 50)
    private String rolDestinatario;
    @Column(name = "es_leido", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean esLeido;
    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="id_notificacion", nullable = true)
    private Notificacion notificacion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="id_infraccion", nullable = true)
    private Infraccion infraccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="id_vehiculo", nullable = true)
    private Vehiculo vehiculo;


    public Alerta(String uuid) { this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        this.setUuid(UUID.randomUUID().toString());
    }

}
