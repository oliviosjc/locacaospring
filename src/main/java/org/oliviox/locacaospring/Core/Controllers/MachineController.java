package org.oliviox.locacaospring.Core.Controllers;

import org.oliviox.locacaospring.Application.DTO.Request.Machine.CreateMachineDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;
import org.oliviox.locacaospring.Application.Services.Interfaces.IMachineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController()
@RequestMapping("api/machine")
public class MachineController
{
    private final IMachineService machineService;

    public MachineController(IMachineService machineService)
    {
        this.machineService = machineService;
    }

    @PostMapping("/create")
    public ResponseEntity<CompletableFuture<ResponseBaseDTO<UUID>>> createMachine(@RequestBody CreateMachineDTO dto) {
        CompletableFuture<ResponseBaseDTO<UUID>> futureResponse = machineService.create(dto);
        return ResponseEntity.status(futureResponse.join().getStatusCode()).body(futureResponse);
    }
}
