package org.oliviox.locacaospring.Domain.Entities.Base;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.oliviox.locacaospring.Domain.Entities.User.User;
import org.oliviox.locacaospring.Infraestructure.Interceptors.BaseEntityInterceptor;

import java.time.LocalDateTime;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@EntityListeners(BaseEntityInterceptor.class)
@EqualsAndHashCode(of = "id")
public class BaseEntity
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "name", length = 255, unique = true, nullable = true)
    private String name;

    @Column(name = "createdAt", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt", updatable = false, nullable = false)
    private LocalDateTime updatedAt;

    public void setName(String name)
    {
        this.name = name.toUpperCase();
    }
}
