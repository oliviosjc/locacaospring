package org.oliviox.locacaospring.Application.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.oliviox.locacaospring.Application.Services.Interfaces.IAuthorizationService;
import org.oliviox.locacaospring.Domain.Entities.User.User;
import org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AuthorizationService implements IAuthorizationService
{
    private final IUserRepository userRepository;

    @Value("${api.security.token.secretKey}")
    private String secretKey;

    public AuthorizationService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return this.userRepository.findByLogin(username);
    }

    @Override
    public String generateToken(User user)
    {
        try {
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
            return JWT.create()
                    .withIssuer("spring-api")
                    .withSubject(user.getLogin())
                    .withExpiresAt(generateExpiration())
                    .sign(algorithm);
        }
        catch (JWTCreationException exception)
        {
            throw new RuntimeException("Error while generating token", exception);
        }
    }

    @Override
    public String validateToken(String token)
    {
        try
        {
            Algorithm algorithm = Algorithm.HMAC256(this.secretKey);
            return JWT.require(algorithm)
                    .withIssuer("spring-api")
                    .build()
                    .verify(token)
                    .getSubject();
        }
        catch (JWTVerificationException exception)
        {
            return "";
        }
    }

    private Instant generateExpiration()
    {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
