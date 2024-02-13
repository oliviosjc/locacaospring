package org.oliviox.locacaospring.Application.Services;

import org.oliviox.locacaospring.Application.DTO.Brand.CreateBrandDTO;
import org.oliviox.locacaospring.Application.DTO.Response.ResponseDTO;
import org.oliviox.locacaospring.Application.Services.Interfaces.IBrandService;
import org.oliviox.locacaospring.Domain.Entities.Brand.Brand;
import org.oliviox.locacaospring.Domain.Specifications.Base.BaseSpecification;
import org.oliviox.locacaospring.Domain.Specifications.Brand.BrandByNameSpecification;
import org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces.IBrandRepository;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class BrandService implements IBrandService
{
    private final IBrandRepository brandRepository;

    public BrandService(IBrandRepository brandRepository)
    {
        this.brandRepository = brandRepository;
    }

    @Override
    @Transactional
    @Async
    public CompletableFuture<ResponseDTO<UUID>> create(@Valid CreateBrandDTO dto)
    {
        return CompletableFuture.supplyAsync(() ->
        {
            BaseSpecification<Brand> specification = new BrandByNameSpecification(dto.getName());
            Iterable<Brand> brands = brandRepository.findAll(specification.resolve());

            if (brands.iterator().hasNext())
                return new ResponseDTO<>("This brand already exists in the database with this name.", HttpStatus.BAD_REQUEST);

            Brand brand = brandRepository.save( new Brand(dto.getName()));
            return new ResponseDTO<>("Success", HttpStatus.CREATED, brand.getId());
        });
    }
}
