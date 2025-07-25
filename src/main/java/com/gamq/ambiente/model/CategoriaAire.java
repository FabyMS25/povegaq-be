package com.gamq.ambiente.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name="categoria_aire", indexes= @Index(name = "idx_caai_uuid", columnList ="uuid", unique= true ))
@SQLDelete(sql = "UPDATE categoria_aire SET estado=true WHERE id_categoria_aire=?")
@Where(clause = "estado = false")
public class CategoriaAire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria_aire")
    private Long idCategoriaAire;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "valor_minimo", nullable = false)
    private Integer valorMinimo;
    @Column(name = "valor_maximo", nullable = false)
    private Integer valorMaximo;
    @Column(nullable = false, length = 20)
    private String categoria;
    @Column(columnDefinition = "TEXT")
    private String descripcion;
    @Column(name = "color")
    private String color;
    @Column(name = "norma")
    private String norma;
    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    public CategoriaAire(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.setUuid(UUID.randomUUID().toString());
        }
    }
}
