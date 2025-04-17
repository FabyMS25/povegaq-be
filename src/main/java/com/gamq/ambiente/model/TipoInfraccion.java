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
@Table(name = "tipo_infracciones", indexes = @Index(name = "idx_tiin", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE tipo_infracciones SET estado=true WHERE id_tipo_infraccion=?")
@Where(clause = "estado = false")
public class TipoInfraccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_infraccion")
    private Long idTipoInfraccion;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "grado", nullable = false, length = 250)
    private String grado;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio", nullable = false)
    private Date fechaInicio;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_fin")
    private Date fechaFin;
    @Column(name = "valor_ufv", precision = 20, scale = 4)
    private BigDecimal valorUFV;
    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_contribuyente", nullable = false)
    private TipoContribuyente tipoContribuyente;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "tipoInfraccion", fetch = FetchType.LAZY)
    private List<Infraccion> infraccionList = new ArrayList<Infraccion>();


    public TipoInfraccion(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        this.setUuid(UUID.randomUUID().toString());
    }

}
