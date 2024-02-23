package org.oliviox.locacaospring.Application.Services;

import org.oliviox.locacaospring.Application.DTO.Request.Brand.CreateBrandDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;
import org.oliviox.locacaospring.Application.Services.Interfaces.IAuthorizationService;
import org.oliviox.locacaospring.Application.Services.Interfaces.IBrandService;
import org.oliviox.locacaospring.Domain.Entities.Brand.Brand;
import org.oliviox.locacaospring.Domain.Entities.User.User;
import org.oliviox.locacaospring.Domain.Specifications.Base.BaseSpecification;
import org.oliviox.locacaospring.Domain.Specifications.Brand.BrandByNameSpecification;
import org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces.IBrandRepository;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class BrandService implements IBrandService
{
    private final IBrandRepository brandRepository;
    private final IAuthorizationService authorizationService;

    public BrandService(IBrandRepository brandRepository, IAuthorizationService authorizationService)
    {
        this.brandRepository = brandRepository;
        this.authorizationService = authorizationService;
    }

    @Override
    @Transactional
    @Async
    public CompletableFuture<ResponseBaseDTO<UUID>> create(CreateBrandDTO dto)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            User loggedUser = this.authorizationService.getLoggedUser();
            BaseSpecification<Brand> specification = new BrandByNameSpecification(dto.name);
            Iterable<Brand> brands = this.brandRepository.findAll(specification.resolve());

            if (brands.iterator().hasNext())
                return new ResponseBaseDTO<>("This brand already exists in the database with this name.", HttpStatus.BAD_REQUEST);

            Brand brand = brandRepository.save( new Brand(dto.name, loggedUser));
            return new ResponseBaseDTO<>("Success", HttpStatus.CREATED, brand.getId());
        });
    }
}
