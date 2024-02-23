package org.oliviox.locacaospring.Application.DTO.Request.Machine;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CreateMachineUnitMaintenanceDTO
{
    public UUID machineUnitId;

    public BigDecimal value;

    public LocalDate entryDate;

    public LocalDate exitDate;

    public String maintenanceDescription;
}
