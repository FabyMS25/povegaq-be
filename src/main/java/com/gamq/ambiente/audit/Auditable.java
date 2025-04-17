package com.gamq.ambiente.audit;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class Auditable<U> {

    @CreatedBy
    protected U createdBy;
    @LastModifiedBy
    protected U modifiedBy;
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createdAt;
    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    protected Date modifiedAt;

}
