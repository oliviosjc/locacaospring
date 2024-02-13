package org.oliviox.locacaospring.Application.Services.Interfaces;

import org.oliviox.locacaospring.Application.DTO.Brand.CreateBrandDTO;
import org.oliviox.locacaospring.Application.DTO.Response.ResponseDTO;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IBrandService
{
    CompletableFuture<ResponseDTO<UUID>> create(CreateBrandDTO dto);
}
