package com.nashtech.rookies.ecommerce.models.key;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;


@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@NoArgsConstructor
@Getter
public abstract class AuditEntity<P extends Serializable> extends IdEntity<P> {

    @CreatedDate
    LocalDateTime createOn;

    @LastModifiedDate
    LocalDateTime lastUpdatedOn;

    @Transient
    @Override
    public boolean isNew () {
        return null == getId();
    }
}