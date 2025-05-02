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
@Table(name = "propietarios", indexes = @Index(name = "idx_prop", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE propietarios SET estado=true WHERE id_propietario=?")
@Where(clause = "estado = false")
public class Propietario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_propietario")
    private Long idPropietario;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "nombre_completo",  nullable = true, length = 250)
    private String nombreCompleto;
    @Column(name = "nro_documento", nullable = true, length = 15)
    private String nroDocumento;
    @Column(name = "tipo_documento", nullable = false)
    private Integer tipoDocumento;
    @Column(name = "expedido", nullable = false)
    private Integer expedido;
    @Column(name= "email", nullable = false, length = 50)
    private String email;
    @Column(name = "nro_telefono", length = 15)
    private String nroTelefono;
    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "propietario", fetch = FetchType.LAZY)
    private List<Vehiculo> vehiculoList = new ArrayList<Vehiculo>();

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "id_tipo_contribuyente", nullable = false)
    private TipoContribuyente tipoContribuyente;

    public Propietario(String uuid) { this.uuid = uuid;}


    @PrePersist
    public void initializeUuid() {
        this.setUuid(UUID.randomUUID().toString());
    }



}
