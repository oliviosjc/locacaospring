package org.oliviox.locacaospring.Domain.Specifications.Machine;

import jakarta.persistence.criteria.Expression;
import org.oliviox.locacaospring.Domain.Entities.Machine.Machine;
import org.oliviox.locacaospring.Domain.Specifications.Base.BaseSpecification;
import org.springframework.data.jpa.domain.Specification;
public class MachineByNameSpecification implements BaseSpecification<Machine>
{
    private final String name;

    public MachineByNameSpecification(String name)
    {
        this.name = name.toUpperCase();
    }

    @Override
    public Specification<Machine> resolve()
    {
        return (root, query, criteriaBuilder) ->
        {
            Expression<String> nameExpression = criteriaBuilder.upper(root.get("Name"));
            return criteriaBuilder.equal(nameExpression, this.name);
        };
    }
}
