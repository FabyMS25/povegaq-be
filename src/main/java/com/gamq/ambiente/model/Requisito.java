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
@Table(name = "requisitos", indexes = @Index(name = "idx_requ", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE requisitos SET estado=true WHERE id_requisito=?")
@Where(clause = "estado = false")
public class Requisito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_requisito")
    private Long idRequisito;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "descripcion", nullable = false, length = 150)
    private String descripcion;
    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "requisito", fetch = FetchType.LAZY)
    private List<RequisitoInspeccion> requisitoInspeccionList = new ArrayList<RequisitoInspeccion>();


    public Requisito(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        this.setUuid(UUID.randomUUID().toString());
    }
}
