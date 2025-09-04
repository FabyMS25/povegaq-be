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
@Table(name = "estaciones", indexes = @Index(name = "idx_esta", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE estaciones SET estado=true WHERE id_estacion=?")
@Where(clause = "estado = false")
public class Estacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estacion")
    private Long idEstacion;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo; // Ej: "Fija", "Móvil", "Automática"
    @Column(name = "ubicacion", nullable = false, length = 250)
    private String ubicacion;
    @Column(name = "descripcion", length = 250)
    private String descripcion;
    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "estacion", fetch = FetchType.LAZY)
    private List<MedicionAire> medicionAireList = new ArrayList<MedicionAire>();

    public Estacion(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.setUuid(UUID.randomUUID().toString());
        }
    }
}
