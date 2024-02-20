package org.oliviox.locacaospring.Application.Services;

import org.oliviox.locacaospring.Application.DTO.Request.Machine.CreateMachineUnitMaintenanceDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;
import org.oliviox.locacaospring.Application.Services.Interfaces.IMachineUnitMaintenanceService;
import org.oliviox.locacaospring.Domain.Entities.Machine.MachineUnit;
import org.oliviox.locacaospring.Domain.Entities.Machine.MachineUnitMaintenance;
import org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces.IMachineUnitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MachineUnitMaintenanceService implements IMachineUnitMaintenanceService
{
    private final IMachineUnitRepository machineUnitRepository;

    public MachineUnitMaintenanceService(IMachineUnitRepository machineUnitRepository)
    {
        this.machineUnitRepository = machineUnitRepository;
    }

    @Override
    @Async
    @Transactional
    public CompletableFuture<ResponseBaseDTO<UUID>> create(@Valid CreateMachineUnitMaintenanceDTO dto) {
        return CompletableFuture.supplyAsync(() -> {
            Optional<MachineUnit> optionalMachineUnit = this.machineUnitRepository.findById(dto.getMachineUnitId());

            if (optionalMachineUnit.isEmpty())
                return new ResponseBaseDTO<>("This machine unit with this id was not found in the base.", HttpStatus.BAD_REQUEST, null);

            MachineUnit machineUnit = optionalMachineUnit.get();

            MachineUnitMaintenance machineUnitMaintenance = new MachineUnitMaintenance(dto.getValue(), dto.getEntryDate(),
                    dto.getExitDate(), dto.getMaintenanceDescription());

            machineUnit.add(machineUnitMaintenance);
            this.machineUnitRepository.save(machineUnit);
            return new ResponseBaseDTO<>("Success", HttpStatus.CREATED);
        });
    }
}
