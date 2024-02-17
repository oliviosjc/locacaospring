package org.oliviox.locacaospring.Application.DTO.Request.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.oliviox.locacaospring.Domain.Entities.User.UserRole;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO
{
    public String login;

    public String password;

    public UserRole role;
}
