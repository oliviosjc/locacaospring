package org.oliviox.locacaospring.Application.Services.Interfaces;

import org.oliviox.locacaospring.Application.DTO.Request.Machine.CreateMachineUnitDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IMachineUnitService
{
    CompletableFuture<ResponseBaseDTO<UUID>> create(CreateMachineUnitDTO dto);
    CompletableFuture<ResponseBaseDTO<UUID>> delete(UUID machineUnitId);
}
