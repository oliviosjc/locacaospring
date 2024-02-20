package org.oliviox.locacaospring.Application.DTO.Request.Machine;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class CreateMachineUnitMaintenanceDTO
{
    @NotNull(message = "The machineUnitId cannot be null.")
    public UUID machineUnitId;

    @NotEmpty(message = "The value cannot be empty.")
    @NotNull(message = "The value cannot be null.")
    public BigDecimal value;

    @NotNull(message = "The entryDate cannot be null.")
    public LocalDate entryDate;

    @NotNull(message = "The exitDate cannot be null.")
    public LocalDate exitDate;

    @Size(max = 255, message = "The maintenance description must be contains 255 characters.")
    public String maintenanceDescription;
}
