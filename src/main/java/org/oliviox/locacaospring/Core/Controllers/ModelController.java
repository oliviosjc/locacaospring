package org.oliviox.locacaospring.Core.Controllers;

import org.oliviox.locacaospring.Application.DTO.Request.Model.CreateModelDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;
import org.oliviox.locacaospring.Application.Services.Interfaces.IModelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/model")
public class ModelController
{
    private final IModelService modelService;

    public ModelController(IModelService modelService)
    {
        this.modelService = modelService;
    }

    @PostMapping("/create")
    public ResponseEntity<CompletableFuture<ResponseBaseDTO<UUID>>> createModel(@RequestBody CreateModelDTO dto)
    {
        CompletableFuture<ResponseBaseDTO<UUID>> futureResponse = this.modelService.create(dto);
        return ResponseEntity.status(futureResponse.join().getStatusCode()).body(futureResponse);
    }
}
