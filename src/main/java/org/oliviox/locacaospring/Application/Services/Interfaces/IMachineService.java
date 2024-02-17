package org.oliviox.locacaospring.Application.Services.Interfaces;

import org.oliviox.locacaospring.Application.DTO.Request.Machine.CreateMachineDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IMachineService
{
    CompletableFuture<ResponseBaseDTO<UUID>> create(CreateMachineDTO dto);
}
