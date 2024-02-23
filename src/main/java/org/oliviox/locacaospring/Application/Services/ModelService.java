package org.oliviox.locacaospring.Application.Services;

import org.oliviox.locacaospring.Application.DTO.Request.Model.CreateModelDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;
import org.oliviox.locacaospring.Application.Services.Interfaces.IModelService;
import org.oliviox.locacaospring.Domain.Entities.Model.Model;
import org.oliviox.locacaospring.Domain.Specifications.Base.BaseSpecification;
import org.oliviox.locacaospring.Domain.Specifications.Model.ModelByNameSpecification;
import org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces.IModelRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ModelService implements IModelService
{
    private final IModelRepository modelRepository;

    public ModelService(IModelRepository modelRepository)
    {
        this.modelRepository = modelRepository;
    }

    @Override
    public CompletableFuture<ResponseBaseDTO<UUID>> create(CreateModelDTO dto)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            BaseSpecification<Model> specification = new ModelByNameSpecification(dto.name);
            Iterable<Model> models = this.modelRepository.findAll(specification.resolve());

            if (models.iterator().hasNext())
                return new ResponseBaseDTO<>("This model already exists in the database with this name.", HttpStatus.BAD_REQUEST);

            this.modelRepository.save(new Model(dto.name));
            return new ResponseBaseDTO<>("Success", HttpStatus.CREATED);
        });
    }
}
