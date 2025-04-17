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
  //  @Column(name = "status_inspeccion", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
   // private boolean statusInspeccion;
    @Column(name = "valor", precision = 20, scale = 4)
    private BigDecimal valor;


   /*
    @Column(name = "co_monoxido_carbono", precision = 20, scale = 4)
    private BigDecimal co;
    @Column(name = "hc_hidrocarburo", precision = 20, scale = 4)
    private BigDecimal hc;
    @Column(name = "co2_dioxido_carbono", precision = 20, scale = 4)
    private BigDecimal co2;


    @Column(name = "hc2_hidrocarburo_2atomos", precision = 20, scale = 4)
    private BigDecimal hc2;

    @Column(name = "o2_oxigeno", precision = 20, scale = 4)
    private BigDecimal o2;
    @Column(name = "no2_dioxido_nitrogeno", precision = 20, scale = 4)
    private BigDecimal no2;
    @Column(name = "lambda", precision = 20, scale = 4)
    private BigDecimal lambda;
    @Column(name = "rpm_revoluciones_minuto", precision = 20, scale = 4)
    private BigDecimal rpm;
    @Column(name = "temperatura_aceite", precision = 20, scale = 4)
    private BigDecimal temperatura_aceite;*/



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
