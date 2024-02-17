package org.oliviox.locacaospring.Application.Services.Interfaces;

import org.oliviox.locacaospring.Application.DTO.Request.Brand.CreateBrandDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IBrandService
{
    CompletableFuture<ResponseBaseDTO<UUID>> create(CreateBrandDTO dto);
}
