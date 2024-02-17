package org.oliviox.locacaospring.Core.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.oliviox.locacaospring.Application.Services.Interfaces.IAuthorizationService;
import org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces.IUserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter
{
    private final IAuthorizationService authorizationService;
    private final IUserRepository userRepository;

    public SecurityFilter(IAuthorizationService authorizationService,
                          IUserRepository userRepository)
    {
        this.authorizationService = authorizationService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException
    {
        var token = this.recoverToken(request);
        if(token != null)
        {
            var login = authorizationService.validateToken(token);
            UserDetails user = this.userRepository.findByLogin(login);

            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request)
    {
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer", "");
    }
}
