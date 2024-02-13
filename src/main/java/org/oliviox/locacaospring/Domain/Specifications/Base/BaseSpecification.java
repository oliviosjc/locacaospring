package org.oliviox.locacaospring.Domain.Specifications.Base;

import org.springframework.data.jpa.domain.Specification;

public interface BaseSpecification<T>
{
    Specification<T> resolve();
}