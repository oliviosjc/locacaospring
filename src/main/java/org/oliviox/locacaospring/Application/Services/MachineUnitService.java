package org.oliviox.locacaospring.Application.Services;

import org.oliviox.locacaospring.Application.DTO.Request.Machine.CreateMachineUnitDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;
import org.oliviox.locacaospring.Application.Services.Interfaces.IAuthorizationService;
import org.oliviox.locacaospring.Application.Services.Interfaces.IMachineUnitService;
import org.oliviox.locacaospring.Domain.Entities.Machine.Machine;
import org.oliviox.locacaospring.Domain.Entities.Machine.MachineUnit;
import org.oliviox.locacaospring.Domain.Entities.User.User;
import org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces.IMachineRepository;
import org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces.IMachineUnitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class MachineUnitService implements IMachineUnitService
{
    private final IMachineRepository machineRepository;
    private final IMachineUnitRepository machineUnitRepository;
    private final IAuthorizationService authorizationService;
    public MachineUnitService(IMachineRepository machineRepository,
                              IMachineUnitRepository machineUnitRepository,
                              IAuthorizationService authorizationService)
    {
        this.machineRepository = machineRepository;
        this.machineUnitRepository = machineUnitRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    @Async
    @Transactional
    public CompletableFuture<ResponseBaseDTO<UUID>> create(CreateMachineUnitDTO dto)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            User loggedUser = this.authorizationService.getLoggedUser();
            Optional<Machine> optionalMachine = this.machineRepository.findById(dto.machineId);
            if (optionalMachine.isEmpty())
                return new ResponseBaseDTO<>("This machine with this id was not found in the base.", HttpStatus.BAD_REQUEST, null);
            Machine machine = optionalMachine.get();

            MachineUnit machineUnit = this.machineUnitRepository.getMachineUnitByExternalIdentifier(dto.externalIdentifier);
            if(machineUnit != null)
            {
                return new ResponseBaseDTO<>("This machine unit with this external identifier already exists in the base.", HttpStatus.BAD_REQUEST, null);
            }

            machineUnit = new MachineUnit
                    (dto.purchasePrice, dto.purchaseDate, dto.externalIdentifier,
                     dto.principalColor, dto.secondaryColor,"", loggedUser);

            machine.add(machineUnit);
            this.machineRepository.save(machine);
            return new ResponseBaseDTO<>("Success", HttpStatus.CREATED);
        });
    }

    @Override
    @Async
    @Transactional
    public CompletableFuture<ResponseBaseDTO<UUID>> delete(UUID machineUnitId)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            Optional<MachineUnit> optionalMachineUnit = this.machineUnitRepository.findById(machineUnitId);
            if(optionalMachineUnit.isEmpty())
                return new ResponseBaseDTO<>("This machine unit with this id was not found in the base.", HttpStatus.BAD_REQUEST, null);

            MachineUnit machineUnit = optionalMachineUnit.get();
            Machine machine = machineUnit.getMachine();

            machine.delete(machineUnit);
            this.machineRepository.save(machine);
            this.machineUnitRepository.delete(machineUnit);

            return new ResponseBaseDTO<>("Machine unit deleted successfully.", HttpStatus.OK);
        });
    }
}
