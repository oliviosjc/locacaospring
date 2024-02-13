package org.oliviox.locacaospring.Domain.Specifications.Machine;

import jakarta.persistence.criteria.JoinType;
import org.oliviox.locacaospring.Domain.Entities.Machine.Machine;
import org.oliviox.locacaospring.Domain.Specifications.Base.BaseSpecification;
import org.springframework.data.jpa.domain.Specification;

public class MachineByIdWithUnitsSpecification implements BaseSpecification<Machine> {

    private final Long machineId;

    public MachineByIdWithUnitsSpecification(Long machineId) {
        this.machineId = machineId;
    }

    public Specification<Machine> resolve()
    {
        return (root, query, criteriaBuilder) ->
        {
            root.fetch("machineUnits", JoinType.LEFT);
            return criteriaBuilder.equal(root.get("id"), machineId);
        };
    }
}
