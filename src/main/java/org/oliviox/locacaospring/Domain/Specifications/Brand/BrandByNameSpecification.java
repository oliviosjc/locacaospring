package org.oliviox.locacaospring.Domain.Specifications.Brand;

import jakarta.persistence.criteria.Expression;
import org.oliviox.locacaospring.Domain.Entities.Brand.Brand;
import org.oliviox.locacaospring.Domain.Specifications.Base.BaseSpecification;
import org.springframework.data.jpa.domain.Specification;

public class BrandByNameSpecification implements BaseSpecification<Brand>
{
    private final String name;

    public BrandByNameSpecification(String name)
    {
        this.name = name.toUpperCase();
    }

    @Override
    public Specification<Brand> resolve()
    {
        return (root, query, criteriaBuilder) ->
        {
            Expression<String> nameExpression = criteriaBuilder.upper(root.get("name"));
            return criteriaBuilder.equal(nameExpression, this.name);
        };
    }
}
