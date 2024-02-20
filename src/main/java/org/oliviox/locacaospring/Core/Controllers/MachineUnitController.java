package org.oliviox.locacaospring.Core.Controllers;

import org.oliviox.locacaospring.Application.DTO.Request.Machine.CreateMachineUnitDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;
import org.oliviox.locacaospring.Application.Services.Interfaces.IMachineUnitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController()
@RequestMapping("api/machineUnit")
public class MachineUnitController
{
    private final IMachineUnitService machineUnitService;

    public MachineUnitController(IMachineUnitService machineUnitService)
    {
        this.machineUnitService = machineUnitService;
    }

    @PostMapping("/create")
    public ResponseEntity<CompletableFuture<ResponseBaseDTO<UUID>>> createMachineUnit(@RequestBody CreateMachineUnitDTO dto)
    {
        CompletableFuture<ResponseBaseDTO<UUID>> futureResponse = this.machineUnitService.create(dto);
        return ResponseEntity.status(futureResponse.join().getStatusCode()).body(futureResponse);
    }

    @DeleteMapping("/delete/{machineUnitId}")
    public ResponseEntity<CompletableFuture<ResponseBaseDTO<UUID>>> deleteMachine(@PathVariable UUID machineUnitId)
    {
        CompletableFuture<ResponseBaseDTO<UUID>> futureResponse = this.machineUnitService.delete(machineUnitId);
        return ResponseEntity.status(futureResponse.join().getStatusCode()).body(futureResponse);
    }
}
