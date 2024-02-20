package org.oliviox.locacaospring.Application.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.oliviox.locacaospring.Application.Services.Interfaces.IAuthorizationService;
import org.oliviox.locacaospring.Domain.Entities.User.User;
import org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
public class AuthorizationService implements IAuthorizationService
{
    private final IUserRepository userRepository;

    @Value("SECRET")
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
        try
        {
            Algorithm algorithm = Algorithm.HMAC256("SECRET");
            return JWT.create()
                    .withIssuer("spring-api")
                    .withSubject(user.getId().toString())
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
            Algorithm algorithm = Algorithm.HMAC256("SECRET");
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

    @Override
    public User getLoggedUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() instanceof User)
        {
            User user = (User) authentication.getPrincipal();
            return user;
        }

        return null;
    }

    private Instant generateExpiration()
    {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
