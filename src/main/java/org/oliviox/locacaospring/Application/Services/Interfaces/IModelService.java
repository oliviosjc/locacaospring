package org.oliviox.locacaospring.Application.Services.Interfaces;

import org.oliviox.locacaospring.Application.DTO.Request.Model.CreateModelDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IModelService
{
    CompletableFuture<ResponseBaseDTO<UUID>> create(CreateModelDTO dto);
}
