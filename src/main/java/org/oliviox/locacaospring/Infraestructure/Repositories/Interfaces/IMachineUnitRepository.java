package org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces;

import org.oliviox.locacaospring.Domain.Entities.Machine.Machine;
import org.oliviox.locacaospring.Domain.Entities.Machine.MachineUnit;
import org.oliviox.locacaospring.Infraestructure.Repositories.IGenericRepository;

public interface IMachineUnitRepository extends IGenericRepository<MachineUnit>
{
    MachineUnit getMachineUnitByExternalIdentifier(String externalIdentifier);
}
