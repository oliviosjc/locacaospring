package org.oliviox.locacaospring.Application.Services;

import org.oliviox.locacaospring.Application.DTO.Request.Machine.CreateMachineUnitDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;
import org.oliviox.locacaospring.Application.Services.Interfaces.IMachineUnitService;
import org.oliviox.locacaospring.Domain.Entities.Machine.Machine;
import org.oliviox.locacaospring.Domain.Entities.Machine.MachineUnit;
import org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces.IMachineRepository;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class MachineUnitService implements IMachineUnitService
{
    private final IMachineRepository machineRepository;
    public MachineUnitService(IMachineRepository machineRepository)
    {
        this.machineRepository = machineRepository;
    }

    @Override
    @Async
    @Transactional
    public CompletableFuture<ResponseBaseDTO<UUID>> create(@Valid CreateMachineUnitDTO dto) {
        return CompletableFuture.supplyAsync(() -> {
            Optional<Machine> optionalMachine = this.machineRepository.findById(dto.getMachineId());
            if (optionalMachine.isEmpty())
                return new ResponseBaseDTO<>("This machine with this id was not found in the base.", HttpStatus.BAD_REQUEST, null);

            Machine machine = optionalMachine.get();
            MachineUnit machineUnit = new MachineUnit(dto.getPurchasePrice(), dto.getPurchaseDate());
            machine.add(machineUnit);
            this.machineRepository.save(machine);
            return new ResponseBaseDTO<>("Success", HttpStatus.CREATED);
        });
    }
}
