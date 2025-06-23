package com.gamq.ambiente.model;

//import com.gamq.ambiente.enumeration.TipoCombustible;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "detalle_inspecciones", indexes = @Index(name = "idx_dein", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE detalle_inspecciones SET estado=true WHERE id_detalle_inspeccion=?")
@Where(clause = "estado = false")
public class DetalleInspeccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_inspeccion")
    private Long idDetalleInspeccion;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "valor", nullable = false, precision = 20, scale = 4)
    private BigDecimal valor;

    @Column(name = "resultado_parcial", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean resultadoParcial;
    @Column(name = "tipo_prueba", nullable = false)
    private Integer tipoPrueba;  //movil o estatica

    @Column(name = "nro_ejecucion", nullable = false)
    private Integer nroEjecucion;

    @Column(name = "limite_permisible",  nullable = false, precision = 20, scale = 4)
    private BigDecimal limitePermisible;

   // tabla 2025
   /* @Column(name = "modo_combustion", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private TipoCombustible modoCombustion;*/

    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_inspeccion", nullable = false)
    private Inspeccion inspeccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_parametro", nullable = false)
    private TipoParametro tipoParametro;

    public DetalleInspeccion(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        this.setUuid(UUID.randomUUID().toString());
    }
}
