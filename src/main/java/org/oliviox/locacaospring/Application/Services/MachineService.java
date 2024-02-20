package org.oliviox.locacaospring.Application.Services;

import org.oliviox.locacaospring.Application.DTO.Request.Machine.CreateMachineDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;
import org.oliviox.locacaospring.Application.Services.Interfaces.IAuthorizationService;
import org.oliviox.locacaospring.Application.Services.Interfaces.IMachineService;
import org.oliviox.locacaospring.Domain.Entities.Brand.Brand;
import org.oliviox.locacaospring.Domain.Entities.Machine.Machine;
import org.oliviox.locacaospring.Domain.Entities.User.User;
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
    private final IAuthorizationService authorizationService;

    public MachineService(IMachineRepository machineRepository,
                          IBrandRepository brandRepository, IAuthorizationService authorizationService)
    {
        this.machineRepository = machineRepository;
        this.brandRepository = brandRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    @Transactional
    @Async
    public CompletableFuture<ResponseBaseDTO<UUID>> create(@Valid CreateMachineDTO dto)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            User loggedUser = this.authorizationService.getLoggedUser();
            BaseSpecification<Machine> specification = new MachineByNameSpecification(dto.getName());
            Iterable<Machine> machines = machineRepository.findAll(specification.resolve());

            if (machines.iterator().hasNext())
                return new ResponseBaseDTO<>("This machine already exists in the database with this name.", HttpStatus.BAD_REQUEST, null);

            UUID brandId = UUID.fromString(dto.getBrandId());
            Optional<Brand> brand = brandRepository.findById(brandId);
            if (brand.isEmpty())
                return new ResponseBaseDTO<>("This brand with this id was not found in the database.", HttpStatus.BAD_REQUEST, null);

            Machine machine = machineRepository.save(new Machine(dto.getName(), brand.get(), loggedUser));
            return new ResponseBaseDTO<>("Success", HttpStatus.CREATED, machine.getId());
        });
    }

    @Override
    @Async
    @Transactional
    public CompletableFuture<ResponseBaseDTO<UUID>> delete(UUID machineId)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            Optional<Machine> optionalMachine = this.machineRepository.findById(machineId);
            if(optionalMachine.isEmpty())
                return new ResponseBaseDTO<>("This machine with this id was not found in the base.", HttpStatus.BAD_REQUEST, null);

            this.machineRepository.delete(optionalMachine.get());
            return new ResponseBaseDTO<>("The machine was deleted successfully in the base.", HttpStatus.OK, null);
        });
    }
}
