package org.oliviox.locacaospring.Application.DTO.Machine;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotBlank;

public class CreateMachineDTO
{
    @NotBlank(message = "The property name cannot be blank.")
    @NotNull(message = "The property name cannot be null.")
    @Size(max = 255, message = "The property name must contain up to 255 characters.")
    public String name;

    @NotNull(message = "The property brandId cannot be null.")
    public Long brandId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }
}
