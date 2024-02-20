package org.oliviox.locacaospring.Infraestructure.Repositories;

import org.oliviox.locacaospring.Domain.Entities.Base.BaseEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IGenericRepository<T extends BaseEntity> extends CrudRepository<T, UUID>, JpaSpecificationExecutor<T>
{
}
