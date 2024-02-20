package org.oliviox.locacaospring.Application.DTO.Request.Machine;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CreateMachineUnitDTO
{
    @NotNull(message = "The property machineId cannot be null.")
    public UUID machineId;

    @NotNull(message = "The property purchasePrice cannot be null.")
    public BigDecimal purchasePrice;

    @NotNull(message = "The property purchaseDate cannot be null.")
    public LocalDate purchaseDate;
}
