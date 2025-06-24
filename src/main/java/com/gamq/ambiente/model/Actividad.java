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
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "actividades", indexes = @Index(name = "idx_acti", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE actividades SET estado=true WHERE id_actividad=?")
@Where(clause = "estado = false")
public class Actividad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actividad")
    private Long idActividad;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name = "tipo_actividad", length = 50)
    private String tipoActividad;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio")
    private Date fechaInicio;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_fin")
    private Date fechaFin;
    @Column(name = "activo", columnDefinition = "BOOLEAN NOT NULL DEFAULT '1'")
    private boolean activo;

    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "actividad", fetch = FetchType.LAZY)
    private List<Inspeccion> inspeccionList = new ArrayList<Inspeccion>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false, mappedBy = "actividad", fetch = FetchType.LAZY)
    private List<Evento> eventoList = new ArrayList<Evento>();

    public Actividad(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        if (this.uuid == null || this.uuid.isEmpty()) {
            this.setUuid(UUID.randomUUID().toString());
        }
    }
}
