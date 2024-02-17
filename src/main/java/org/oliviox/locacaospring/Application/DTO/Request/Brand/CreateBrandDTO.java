package org.oliviox.locacaospring.Application.DTO.Request.Brand;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
public class CreateBrandDTO
{
    @NotBlank(message = "The property name cannot be blank.")
    @NotNull(message = "The property name cannot be null.")
    @Size(max = 255, message = "The property name must contain up to 255 characters.")
    public String name;

}
