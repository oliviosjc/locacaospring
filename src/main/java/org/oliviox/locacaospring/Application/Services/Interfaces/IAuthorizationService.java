package org.oliviox.locacaospring.Application.Services.Interfaces;

import org.oliviox.locacaospring.Domain.Entities.User.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface IAuthorizationService extends UserDetailsService
{
    String generateToken(User user);

    String validateToken(String token);

    User getLoggedUser();
}
