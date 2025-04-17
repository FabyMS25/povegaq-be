package com.gamq.ambiente.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Entity
@Table(name = "ufvs", indexes = @Index(name = "idx_ufvu", columnList = "uuid", unique = true))
@SQLDelete(sql = "UPDATE ufvs SET estado=true WHERE id_ufv=?")
@Where(clause = "estado = false")
public class Ufv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ufv")
    private Long idUfv;
    @Column(name = "uuid", unique = true, nullable = false, length = 64)
    private String uuid;
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha", unique = true, nullable = false)
    private Date fecha;
    @Column(name = "valor", precision = 12, scale = 5)
    private BigDecimal valor;

    @Column(name = "estado", columnDefinition = "BOOLEAN NOT NULL DEFAULT '0'")
    private boolean estado;

    public Ufv(String uuid) {this.uuid = uuid;}

    @PrePersist
    public void initializeUuid() {
        this.setUuid(UUID.randomUUID().toString());
    }
}
