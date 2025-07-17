package com.gamq.ambiente.model;

import com.gamq.ambiente.enumeration.GradoInfraccion;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "grado", nullable = false, length = 250)
    private GradoInfraccion grado;
    @Column(name = "descripcion", nullable = false, length = 500)
    private String descripcion;
    @Column(name = "valor_ufv", precision = 20, scale = 4)
    private BigDecimal valorUFV;
    @Column(name = "es_automatico", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean esAutomatico;
    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_contribuyente", nullable = false)
    private TipoContribuyente tipoContribuyente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reglamento", nullable = false)
    private Reglamento reglamento;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "tipoInfraccion", fetch = FetchType.LAZY)
    private List<Infraccion> infraccionList = new ArrayList<Infraccion>();


    public TipoInfraccion(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.setUuid(UUID.randomUUID().toString());
        }
    }

}
