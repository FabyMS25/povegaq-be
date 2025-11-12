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
@Table(name = "conductores", indexes = @Index(name = "idx_cond", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE conductores SET estado=true WHERE id_conductor=?")
@Where(clause = "estado = false")
public class Conductor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conductor")
    private Long idConductor;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "nombre",  nullable = false, length = 100)
    private String nombre;
    @Column(name = "primerApellido",  nullable = false, length = 80)
    private String primerApellido;
    @Column(name = "segundoApellido", length = 80)
    private String segundoApellido;
    @Column(name = "apellidoEsposo", length = 80)
    private String apellidoEsposo;
    @Column(name = "tipo_documento", nullable = false, length = 3)
    private String tipoDocumento;
    @Column(name = "numero_documento", nullable = false, length = 15)
    private String numeroDocumento;
    @Column(name = "expedido", nullable = true)
    private Integer expedido;
    @Column(name= "email", nullable = false, length = 100)
    private String email;
    @Column(name = "nro_telefono", length = 15)
    private String nroTelefono;

    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "id_tipo_contribuyente", nullable = false)
    private TipoContribuyente tipoContribuyente;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "conductor", fetch = FetchType.LAZY)
    private List<Inspeccion> inspeccionList = new ArrayList<Inspeccion>();

    public Conductor(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.setUuid(UUID.randomUUID().toString());
        }
    }
}
