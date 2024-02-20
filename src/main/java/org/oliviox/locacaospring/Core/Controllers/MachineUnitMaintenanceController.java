package org.oliviox.locacaospring.Core.Controllers;

import org.oliviox.locacaospring.Application.DTO.Request.Machine.CreateMachineUnitMaintenanceDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;
import org.oliviox.locacaospring.Application.Services.Interfaces.IMachineUnitMaintenanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController()
@RequestMapping("api/machineUnitMaintenance")
public class MachineUnitMaintenanceController
{
    private final IMachineUnitMaintenanceService machineUnitMaintenanceService;

    public MachineUnitMaintenanceController(IMachineUnitMaintenanceService machineUnitMaintenanceService)
    {
        this.machineUnitMaintenanceService = machineUnitMaintenanceService;
    }

    @PostMapping("/create")
    public ResponseEntity<CompletableFuture<ResponseBaseDTO<UUID>>> createMachineUnit(@RequestBody CreateMachineUnitMaintenanceDTO dto)
    {
        CompletableFuture<ResponseBaseDTO<UUID>> futureResponse = this.machineUnitMaintenanceService.create(dto);
        return ResponseEntity.status(futureResponse.join().getStatusCode()).body(futureResponse);
    }

    @DeleteMapping("/delete/{machineUnitMaintenanceId}")
    public ResponseEntity<CompletableFuture<ResponseBaseDTO<UUID>>> deleteMachine(@PathVariable UUID machineUnitMaintenanceId)
    {
        CompletableFuture<ResponseBaseDTO<UUID>> futureResponse = this.machineUnitMaintenanceService.delete(machineUnitMaintenanceId);
        return ResponseEntity.status(futureResponse.join().getStatusCode()).body(futureResponse);
    }
}
