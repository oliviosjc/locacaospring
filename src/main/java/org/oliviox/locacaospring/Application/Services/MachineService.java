package org.oliviox.locacaospring.Application.Services;

import org.oliviox.locacaospring.Application.DTO.Request.Machine.CreateMachineDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;
import org.oliviox.locacaospring.Application.Services.Interfaces.IMachineService;
import org.oliviox.locacaospring.Domain.Entities.Brand.Brand;
import org.oliviox.locacaospring.Domain.Entities.Machine.Machine;
import org.oliviox.locacaospring.Domain.Specifications.Base.BaseSpecification;
import org.oliviox.locacaospring.Domain.Specifications.Machine.MachineByNameSpecification;
import org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces.IBrandRepository;
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
public class MachineService implements IMachineService
{
    private final IMachineRepository machineRepository;
    private final IBrandRepository brandRepository;

    public MachineService(IMachineRepository machineRepository, IBrandRepository brandRepository) {
        this.machineRepository = machineRepository;
        this.brandRepository = brandRepository;
    }

    @Override
    @Transactional
    @Async
    public CompletableFuture<ResponseBaseDTO<UUID>> create(@Valid CreateMachineDTO dto) {
        return CompletableFuture.supplyAsync(() ->
        {
            BaseSpecification<Machine> specification = new MachineByNameSpecification(dto.name);
            Iterable<Machine> machines = machineRepository.findAll(specification.resolve());

            if (machines.iterator().hasNext())
                return new ResponseBaseDTO<>("This machine already exists in the database with this name.", HttpStatus.BAD_REQUEST, null);

            Optional<Brand> brand = brandRepository.findById(dto.brandId);
            if (brand.isEmpty())
                return new ResponseBaseDTO<>("This brand with this id was not found in the database.", HttpStatus.BAD_REQUEST, null);

            Machine machine = machineRepository.save(new Machine(dto.getName(), brand.get()));
            return new ResponseBaseDTO<>("Success", HttpStatus.CREATED, machine.getId());
        });
    }
}
