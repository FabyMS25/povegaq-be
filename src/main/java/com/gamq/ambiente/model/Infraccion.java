package com.gamq.ambiente.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
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
    private String statusInfraccion; //GENERADA, ENVIADA, PAGADA
    @Column(name = "estado_pago", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estadoPago;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_pago", nullable = true)
    private Date fechaPago;
    @Column(name = "numero_tasa", nullable = true, length = 15)
    private String numeroTasa; // Código que devuelve el sistema externo
    @Column(name = "motivo", nullable = false, length = 250)
    private String motivo;
    @Column(name = "nombre_registrador", nullable = false, length = 100)
    private String nombreRegistrador;
    @Column(name = "uuid_usuario", nullable = false, length = 64)
    private String uuidUsuario;
    @Column(name = "generado_sistema", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean generadoSistema;
    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    // Campo transitorio lógico y no persistente
    @Transient
    private boolean enPlazo;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_infraccion", nullable = false)
    private TipoInfraccion tipoInfraccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo", nullable = false)
    @JsonBackReference
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inspeccion", nullable = true)
    private Inspeccion inspeccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_notificacion", nullable = true)
    private Notificacion notificacion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "infraccion", fetch = FetchType.LAZY)
    private List<Alerta> alertaList = new ArrayList<Alerta>();

    public Infraccion(String uuid) {this.uuid = uuid;}

    @Override
    public String toString() {
        return "Inspeccion{" +
                "idInfraccion=" + idInfraccion +
                ", uuid='" + uuid + '\'' +
                ", fechaInfraccion=" + fechaInfraccion +
                ", montoTotal=" + montoTotal +
                ", statusInfraccion=" + statusInfraccion +
                ", estadoPago=" + estadoPago +
                ", fechaPago=" + fechaPago +
                ", numeroTasa='" + numeroTasa + '\'' +
                ", motivo='" + motivo + '\'' +
                ", nombreRegistrador='" + nombreRegistrador + '\'' +
                ", uuidUsuario='" + uuidUsuario + '\'' +
                ", generadoSistema="+ generadoSistema +
                ", estado=" + estado +
                '}';
    }

    // Getter manual que anula el de Lombok
    public boolean isEnPlazo(int diasPermitidos) {
        if (fechaInfraccion == null) return false;

        LocalDate fecha = fechaInfraccion.toInstant()
                .atZone(ZoneId.systemDefault())// modificar 2026
                .toLocalDate();
        //int diasPermitidos = 10; // o parametrizable según reglas de negocio
        return !LocalDate.now().isAfter(fecha.plusDays(diasPermitidos));
    }

    @PrePersist
    public void initializeUuid() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.setUuid(UUID.randomUUID().toString());
        }
    }
}
