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


@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name="medicion_aire", indexes= @Index(name = "idx_meai_uuid", columnList ="uuid", unique= true ))
@SQLDelete(sql = "UPDATE medicion_aire SET estado=true WHERE id_medicion_aire=?")
@Where(clause = "estado = false")
public class MedicionAire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicion_aire")
    private Long idMedicionAire;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha")
    private Date fecha;
    @Column(name = "mes", nullable = false, length = 100)
    private String mes;
    @Column(name = "dia", nullable = false, length = 100)
    private Integer dia;
    @Column(name = "valor",  nullable = false, precision = 20, scale = 4)
    private BigDecimal valor;
    @Column(name = "estacion",  nullable = true, length = 100)
    private String estacion;

    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    public MedicionAire(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.setUuid(UUID.randomUUID().toString());
        }
    }
}
