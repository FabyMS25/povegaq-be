package com.gamq.ambiente.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "infracciones", indexes = @Index(name = "idx_infr", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE infracciones SET estado=true WHERE id_infraccion=?")
@Where(clause = "estado = false")
public class Infraccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_infraccion")
    private Long idInfraccion;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_infraccion", nullable = false)
    private Date fechaInfraccion;
    @Column(name = "monto_total", precision = 20, scale = 4, nullable = false)
    private BigDecimal montoTotal;
    @Column(name = "status_infraccion", length = 30) //
    private String statusInfraccion;
    @Column(name = "estado_pago", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estadoPago;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_pago", nullable = true)
    private Date fechaPago;
    @Column(name = "numero_tasa", nullable = true, length = 15)
    private String numeroTasa;

    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_infraccion", nullable = false)
    private TipoInfraccion tipoInfraccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inspeccion", nullable = true)
    private Inspeccion inspeccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_notificacion", nullable = true)
    private Notificacion notificacion;

    public Infraccion(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        this.setUuid(UUID.randomUUID().toString());
    }
}
