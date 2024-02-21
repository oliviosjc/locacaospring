package org.oliviox.locacaospring.Application.DTO.Request.Machine;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class CreateMachineDTO
{
    @NotBlank(message = "The property name cannot be blank.")
    @NotNull(message = "The property name cannot be null.")
    @Size(max = 255, message = "The property name must contain up to 255 characters.")
    public String name;

    @NotNull(message = "The property brandId cannot be null.")
    @NotEmpty(message = "The property brandId cannot be null.")
    public UUID brandId;
}
