package com.gamq.ambiente.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "certificados", indexes = @Index(name = "idx_cert", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE certificados SET estado=true WHERE id_certificado=?")
@Where(clause = "estado = false")
public class Certificado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_certificado")
    private Long idCertificado;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Column(name="codigo", nullable = false, length = 64)
    private String codigo;
    @Column(name = "qr", nullable = false)
    private String qrContent;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_emision", nullable = false)
    private Date fechaEmision;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_vencimiento", nullable = false)
    private Date fechaVencimiento;
    @Column(name = "es_valido", columnDefinition = "BOOLEAN NOT NULL DEFAULT '1'")
    private boolean esValido;

    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;


   // @OneToOne()
   // @JoinColumn(name = "id_inspeccion", nullable = false)
   // private Inspeccion inspeccion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="id_inspeccion", nullable = false)
    private Inspeccion inspeccion;

    public Certificado(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        this.setUuid(UUID.randomUUID().toString());
    }
}
