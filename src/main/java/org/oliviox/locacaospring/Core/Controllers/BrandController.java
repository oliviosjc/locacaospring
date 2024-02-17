package org.oliviox.locacaospring.Core.Controllers;

import org.oliviox.locacaospring.Application.DTO.Request.Brand.CreateBrandDTO;
import org.oliviox.locacaospring.Application.DTO.Response.Base.ResponseBaseDTO;
import org.oliviox.locacaospring.Application.Services.Interfaces.IBrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/brand")
public class BrandController
{
    private final IBrandService brandService;

    public BrandController(IBrandService brandService)
    {
        this.brandService = brandService;
    }

    @PostMapping("/create")
    public ResponseEntity<CompletableFuture<ResponseBaseDTO<UUID>>> createBrand(@RequestBody CreateBrandDTO dto)
    {
        CompletableFuture<ResponseBaseDTO<UUID>> futureResponse = brandService.create(dto);
        return ResponseEntity.status(futureResponse.join().getStatusCode()).body(futureResponse);
    }
}
