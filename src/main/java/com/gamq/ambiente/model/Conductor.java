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
    @Column(name = "id_actividad")
    private Long idActividad;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "nombre_completo", nullable = false, length = 250)
    private String nombreCompleto;
    @Column(name = "tipo_contribuyente", nullable = false, length = 10)
    private String tipoContribuyente; //NATURAL, JURIDICO
    @Column(name = "codigo_contribuyente", nullable = false, length = 50)
    private String codigoContribuyente;
    @Column(name = "tipo_documento", nullable = false, length = 3)
    private String tipoDocumento;
    @Column(name = "numero_documento", nullable = false, length = 15)
    private String numeroDocumento;
    @Column(name = "expedido", nullable = false)
    private Integer expedido;
    @Column(name= "email", nullable = false, length = 50)
    private String email;

    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @OneToOne()
    @JoinColumn(name = "id_inspeccion", nullable = false)
    private Inspeccion inspeccion;

    public Conductor(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        this.setUuid(UUID.randomUUID().toString());
    }
}
