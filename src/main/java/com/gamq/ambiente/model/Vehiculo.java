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
@Table(name = "vehiculos", indexes = @Index(name = "idx_vehi", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE vehiculos SET estado=true WHERE id_vehiculo=?")
@Where(clause = "estado = false")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Long idVehiculo;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "placa",nullable = true)
    private String placa;
    @Column(name = "poliza",nullable = true)
    private String poliza;
    @Column(name = "vin_numero_identificacion",nullable = true)
    private String vinNumeroIdentificacion;
    @Column(name = "es_oficial", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean esOficial;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_registro", nullable = false)
    private Date fechaRegistro;
    @Column(name = "juridiccion_origen", nullable = true)
    private String juridiccionOrigen;  //radicatoria LA PAZ QUILLACOLLO TARIJA

    @Column(name = "es_movil", columnDefinition = "BOOLEAN NOT NULL DEFAULT true")
    private Boolean esMovil;
    @Column(name = "es_unidad_industrial", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean esUnidadIndustrial;
    @Column(name = "pin_numero_identificacion",nullable = true)
    private String pinNumeroIdentificacion;
    @Column(name = "categoria_vehicular", nullable = false, length = 150)
    private String categoriaVehicular;

    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "vehiculo", fetch = FetchType.LAZY)
    private List<Inspeccion> inspeccionList = new ArrayList<Inspeccion>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_propietario", nullable = true)
    private Propietario propietario;

    @OneToOne(mappedBy = "vehiculo")
    private DatoTecnico datoTecnico;

    public Vehiculo(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        this.setUuid(UUID.randomUUID().toString());
    }
}
