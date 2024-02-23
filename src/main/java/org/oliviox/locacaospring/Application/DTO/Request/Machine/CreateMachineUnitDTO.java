package org.oliviox.locacaospring.Application.DTO.Request.Machine;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CreateMachineUnitDTO
{
    public UUID machineId;

    public BigDecimal purchasePrice;

    public LocalDate purchaseDate;

    public String externalIdentifier;

    public String principalColor;

    public String secondaryColor;
}
