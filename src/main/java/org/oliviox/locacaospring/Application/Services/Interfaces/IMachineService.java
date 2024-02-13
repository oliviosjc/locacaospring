package org.oliviox.locacaospring.Application.Services.Interfaces;

import org.oliviox.locacaospring.Application.DTO.Machine.CreateMachineDTO;
import org.oliviox.locacaospring.Application.DTO.Response.ResponseDTO;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface IMachineService
{
    CompletableFuture<ResponseDTO<UUID>> create(CreateMachineDTO dto);
}
