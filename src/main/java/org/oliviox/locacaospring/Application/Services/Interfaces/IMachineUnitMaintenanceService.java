package org.oliviox.locacaospring.Application.Services.Interfaces;

import org.oliviox.locacaospring.Application.DTO.Request.Machine.CreateMachineUnitMaintenanceDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IMachineUnitMaintenanceService
{
    CompletableFuture<ResponseBaseDTO<UUID>> create(CreateMachineUnitMaintenanceDTO dto);
}
