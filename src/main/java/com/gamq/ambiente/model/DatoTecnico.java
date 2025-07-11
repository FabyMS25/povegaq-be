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
import java.math.BigInteger;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "datos_tecnicos", indexes = @Index(name = "idx_date", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE datos_tecnicos SET estado=true WHERE id_dato_tecnico=?")
@Where(clause = "estado = false")
public class DatoTecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dato_tecnico")
    private Long idDatoTecnico;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;

    //@Column(name = "clase", nullable = false, length = 50)
    //private String clase;
    @Column(name = "marca", nullable = false, length = 50)
    private String marca;
    @Column(name = "pais", nullable = false, length = 50)
    private String pais; //pais de fabricacion u origen o procedencia
    @Column(name = "traccion", nullable = true, length = 50)
    private String traccion; //delantera (FWD), trasera (RWD), total (AWD) o integral (4WD)
    @Column(name = "modelo", nullable = false, length = 50)
    private String modelo; // el year
    @Column(name = "servicio", nullable = true, length = 50)
    private String servicio; //particular, comercial, flota, etc.
    @Column(name = "cilindrada", precision = 20, scale = 4)
    private BigDecimal cilindrada; //2.5
    @Column(name = "color", nullable = false, length = 50)
    private String color;
    @Column(name = "capacidad_carga", nullable = true, precision = 20, scale=4)
    private BigDecimal capacidadCarga;
    @Column(name = "tipo_vehiculo", nullable = true, length = 50)
    private String tipoVehiculo; //sedán, SUV, camioneta, furgón, camión, eco sport etc
    @Column(name = "tipo_carroceria", nullable = true, length = 50)
    private String tipoCarroceria;
    @Column(name = "year_fabricacion", nullable = false)
    private Integer yearFabricacion;
    @Column(name = "numero_puertas", nullable = true)
    private Integer numeroPuertas;
    @Column(name = "tamano_motor", nullable = true)
    private Integer tamanoMotor; //2500
    @Column(name = "tipo_motor", nullable = false, length = 50)
    private String tipoMotor; //4 cilindros,v6
    @Column(name = "kilometraje", nullable = true, length = 50)
    private BigInteger kilometraje; //150000 km mas grandes en flotas y camiones de largo viaje
    @Column(name = "emision_standard", length = 100)
    private String emisionStandard; //Euro 6
    @Column(name = "clasificacion", nullable = true, length = 150)
    private String clasificacion;
    @Column(name = "numero_asientos", nullable = true)
    private Integer numeroAsientos;
    @Column(name ="tiempo_motor", nullable = false)
    private String tiempoMotor;
    @Column(name ="categoria_vehiculo", nullable = true)
    private String categoriaVehiculo;

    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @OneToOne()
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_clase_vehiculo", nullable = false)
    private TipoClaseVehiculo tipoClaseVehiculo;

    public DatoTecnico(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.setUuid(UUID.randomUUID().toString());
        }
    }
}
