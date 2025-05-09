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
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "tipo_contribuyentes", indexes = @Index(name = "idx_tico", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE tipo_contribuyentes SET estado=true WHERE id_tipo_contribuyente=?")
@Where(clause = "estado = false")
public class TipoContribuyente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_contribuyente")
    private Long idTipoContribuyente;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "descripcion", nullable = false, length = 150)
    private String descripcion;
    @Column(name = "codigo", nullable = true, length = 15)
    private String codigo;
    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "tipoContribuyente", fetch = FetchType.LAZY)
    private List<Propietario> propietarioList = new ArrayList<Propietario>();

    public TipoContribuyente(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        this.setUuid(UUID.randomUUID().toString());
    }
}
