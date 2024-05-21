package com.nashtech.rookies.ecommerce.models.key;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;


@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@NoArgsConstructor
@Getter
public abstract class AuditEntity<P extends Serializable> extends IdEntity<P> {

    @CreatedDate
    LocalDateTime createdOn;

    @LastModifiedDate
    LocalDateTime lastUpdatedOn;

    @Transient
    @Override
    public boolean isNew () {
        return null == getId();
    }

    @PrePersist
    protected void onCreate() {
        createdOn = LocalDateTime.now();
        lastUpdatedOn = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdatedOn = LocalDateTime.now();
    }
}