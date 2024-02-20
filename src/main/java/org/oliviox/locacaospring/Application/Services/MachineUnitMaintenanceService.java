package org.oliviox.locacaospring.Application.Services;

import org.oliviox.locacaospring.Application.DTO.Request.Machine.CreateMachineUnitMaintenanceDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;
import org.oliviox.locacaospring.Application.Services.Interfaces.IAuthorizationService;
import org.oliviox.locacaospring.Application.Services.Interfaces.IMachineUnitMaintenanceService;
import org.oliviox.locacaospring.Domain.Entities.Machine.MachineUnit;
import org.oliviox.locacaospring.Domain.Entities.Machine.MachineUnitMaintenance;
import org.oliviox.locacaospring.Domain.Entities.User.User;
import org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces.IMachineUnitMaintenanceRepository;
import org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces.IMachineUnitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class MachineUnitMaintenanceService implements IMachineUnitMaintenanceService {
    private final IMachineUnitRepository machineUnitRepository;
    private final IMachineUnitMaintenanceRepository machineUnitMaintenanceRepository;
    private final IAuthorizationService authorizationService;

    public MachineUnitMaintenanceService(IMachineUnitRepository machineUnitRepository,
                                         IMachineUnitMaintenanceRepository machineUnitMaintenanceRepository, IAuthorizationService authorizationService)
    {
        this.machineUnitRepository = machineUnitRepository;
        this.machineUnitMaintenanceRepository = machineUnitMaintenanceRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    @Async
    @Transactional
    public CompletableFuture<ResponseBaseDTO<UUID>> create(@Valid CreateMachineUnitMaintenanceDTO dto)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            User loggedUser = authorizationService.getLoggedUser();
            Optional<MachineUnit> optionalMachineUnit = this.machineUnitRepository.findById(dto.getMachineUnitId());

            if (optionalMachineUnit.isEmpty())
                return new ResponseBaseDTO<>("This machine unit with this id was not found in the base.", HttpStatus.BAD_REQUEST, null);

            MachineUnit machineUnit = optionalMachineUnit.get();

            MachineUnitMaintenance machineUnitMaintenance
                    = new MachineUnitMaintenance
                    (dto.getValue(), dto.getEntryDate(), dto.getExitDate(), dto.getMaintenanceDescription(), loggedUser);

            machineUnit.add(machineUnitMaintenance);
            this.machineUnitRepository.save(machineUnit);
            return new ResponseBaseDTO<>("Success", HttpStatus.CREATED, null);
        });
    }

    @Override
    @Async
    @Transactional
    public CompletableFuture<ResponseBaseDTO<UUID>> delete(UUID machineUnitMaintenanceId) {
        return CompletableFuture.supplyAsync(() ->
        {
            Optional<MachineUnitMaintenance> machineUnitMaintenanceOptional = this.machineUnitMaintenanceRepository.findById(machineUnitMaintenanceId);
            if(machineUnitMaintenanceOptional.isEmpty())
                return new ResponseBaseDTO<>("This machine unit maintenance with this id was not found in the base.", HttpStatus.BAD_REQUEST, null);

            MachineUnitMaintenance machineUnitMaintenance = machineUnitMaintenanceOptional.get();
            MachineUnit machineUnit = machineUnitMaintenance.getMachineUnit();

            machineUnit.delete(machineUnitMaintenance);

            this.machineUnitRepository.save(machineUnit);
            this.machineUnitMaintenanceRepository.delete(machineUnitMaintenance);

            return new ResponseBaseDTO<>("Machine unit maintenance deleted successfully.", HttpStatus.OK);
        });
    }
}
