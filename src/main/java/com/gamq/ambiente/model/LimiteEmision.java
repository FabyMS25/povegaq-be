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
@Table(name = "limites_emision", indexes = @Index(name = "idx_liem", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE limites_emision SET estado=true WHERE id_limite_emision=?")
@Where(clause = "estado = false")
public class LimiteEmision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_limite_emision")
    private Long idLimiteEmision;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "tipo_motor", nullable = false, length = 50)
    private String tipoMotor;
    @Column(name = "tipo_combustible", nullable = false, length = 50)
    private String tipoCombustible;

    @Column(name="cilindrada_minimo", nullable = true)
    private Integer cilindradaMinimo;
    @Column(name="cilindrada_maximo", nullable = true)
    private Integer cilindradaMaximo;

    @Column(name ="categoria_vehiculo", nullable = false, length =250)
    private String categoriaVehiculo;  //livianos pesados motocicletas
    @Column(name ="categoria", nullable = true, length = 250)
    private String categoria;


    @Column(name="year_fabricacion_inicio", nullable = true)
    private Integer yearFabricacionInicio; // todos tiene year de fabricacion
    @Column(name="year_fabricacion_fin", nullable = true)
    private Integer yearFabricacionFin;

    @Column(name = "limite",  nullable = false, precision = 20, scale = 4)
    private BigDecimal limite;

    @Column(name = "peso_bruto_minimo", nullable = true)
    private Integer pesoBrutoMinimo; // en kilogramos
    @Column(name = "peso_bruto_maximo", nullable = true)
    private Integer pesoBrutoMaximo; // en kilogramos
    @Column(name = "capacidad_carga_minimo", nullable = true)
    private Integer capacidadCargaMinimo; // en kilogramos
    @Column(name = "capacidad_carga_maximo", nullable = true)
    private Integer capacidadCargaMaximo; // en kilogramos

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio", nullable = false)
    private Date fechaInicio;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_fin")
    private Date fechaFin;
    @Column(name="altitud_minima", nullable = true)
    private Integer altitudMinima;
    @Column(name="altitud_maxima", nullable = true)
    private Integer altitudMaxima;
    @Column(name="clase_vehiculo", nullable = true)
    private String claseVehiculo;
    @Column(name="tiempo_motor", nullable = true)
    private String tiempoMotor;
    @Column(name="ciclo_prueba", nullable = true, length =50)
    private String cicloPrueba;
    @Column(name="normativa", nullable = true,length =50)
    private String normativa;


    @Column(name = "activo", columnDefinition = "BOOLEAN NOT NULL DEFAULT '1'")
    private boolean activo;

    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_parametro", nullable = false)
    private TipoParametro tipoParametro;

    public LimiteEmision(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        this.setUuid(UUID.randomUUID().toString());
    }
}
