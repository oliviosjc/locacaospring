package org.oliviox.locacaospring.Infraestructure.Interceptors;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.oliviox.locacaospring.Domain.Entities.Base.BaseEntity;

import java.time.LocalDateTime;

public class BaseEntityInterceptor
{
    @PrePersist
    public void prePersist(BaseEntity baseEntity)
    {
        LocalDateTime now = LocalDateTime.now();
        baseEntity.setCreatedAt(now);
        baseEntity.setUpdatedAt(now);
    }

    @PreUpdate
    public void preUpdate(BaseEntity baseEntity)
    {
        LocalDateTime now = LocalDateTime.now();
        baseEntity.setUpdatedAt(now);
    }
}
