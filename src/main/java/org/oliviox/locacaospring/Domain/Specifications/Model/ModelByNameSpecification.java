package org.oliviox.locacaospring.Domain.Specifications.Model;

import jakarta.persistence.criteria.Expression;
import org.oliviox.locacaospring.Domain.Entities.Model.Model;
import org.oliviox.locacaospring.Domain.Specifications.Base.BaseSpecification;
import org.springframework.data.jpa.domain.Specification;

public class ModelByNameSpecification implements BaseSpecification<Model>
{
    private final String name;

    public ModelByNameSpecification(String name)
    {
        this.name = name;
    }

    @Override
    public Specification<Model> resolve()
    {
        return (root, query, criteriaBuilder) ->
        {
            Expression<String> nameExpression = criteriaBuilder.upper(root.get("name"));
            return criteriaBuilder.equal(nameExpression, this.name);
        };
    }
}
