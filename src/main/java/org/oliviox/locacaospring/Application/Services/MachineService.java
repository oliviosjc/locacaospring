package org.oliviox.locacaospring.Application.Services;

import org.oliviox.locacaospring.Application.DTO.Request.Machine.CreateMachineDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;
import org.oliviox.locacaospring.Application.Services.Interfaces.IAuthorizationService;
import org.oliviox.locacaospring.Application.Services.Interfaces.IMachineService;
import org.oliviox.locacaospring.Domain.Entities.Brand.Brand;
import org.oliviox.locacaospring.Domain.Entities.Machine.Machine;
import org.oliviox.locacaospring.Domain.Entities.Model.Model;
import org.oliviox.locacaospring.Domain.Entities.User.User;
import org.oliviox.locacaospring.Domain.Specifications.Base.BaseSpecification;
import org.oliviox.locacaospring.Domain.Specifications.Machine.MachineByNameSpecification;
import org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces.IBrandRepository;
import org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces.IMachineRepository;
import org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces.IModelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class MachineService implements IMachineService
{
    private final IMachineRepository machineRepository;
    private final IBrandRepository brandRepository;
    private final IAuthorizationService authorizationService;
    private final IModelRepository modelRepository;

    public MachineService(IMachineRepository machineRepository,
                          IBrandRepository brandRepository,
                          IAuthorizationService authorizationService,
                          IModelRepository modelRepository)
    {
        this.machineRepository = machineRepository;
        this.brandRepository = brandRepository;
        this.authorizationService = authorizationService;
        this.modelRepository = modelRepository;
    }

    @Override
    @Transactional
    @Async
    public CompletableFuture<ResponseBaseDTO<UUID>> create(CreateMachineDTO dto)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            User loggedUser = this.authorizationService.getLoggedUser();
            BaseSpecification<Machine> specification = new MachineByNameSpecification(dto.name);
            Iterable<Machine> machines = machineRepository.findAll(specification.resolve());

            if (machines.iterator().hasNext())
                return new ResponseBaseDTO<>("This machine already exists in the database with this name.", HttpStatus.BAD_REQUEST, null);

            Optional<Brand> brand = this.brandRepository.findById(dto.brandId);
            if (brand.isEmpty())
                return new ResponseBaseDTO<>("This brand with this id was not found in the database.", HttpStatus.BAD_REQUEST, null);

            Optional<Model> model = this.modelRepository.findById(dto.modelId);
            if (model.isEmpty())
                return new ResponseBaseDTO<>("This model with this id was not found in the database.", HttpStatus.BAD_REQUEST, null);

            Machine machine = this.machineRepository.save(new Machine(dto.name, brand.get(), loggedUser, model.get()));
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
