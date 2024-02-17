package org.oliviox.locacaospring.Core.Controllers;

import org.oliviox.locacaospring.Application.DTO.Request.Auth.LoginDTO;
import org.oliviox.locacaospring.Application.DTO.Request.Auth.RegisterDTO;
import org.oliviox.locacaospring.Application.Services.Interfaces.IAuthorizationService;
import org.oliviox.locacaospring.Domain.Entities.User.User;
import org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces.IUserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    private final AuthenticationManager authManager;
    private final IUserRepository userRepository;
    private final IAuthorizationService authorizationService;

    public AuthController(AuthenticationManager authenticationManager,
                          IUserRepository userRepository,
                          IAuthorizationService authorizationService)
    {
        this.authManager = authenticationManager;
        this.userRepository = userRepository;
        this.authorizationService = authorizationService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginDTO dto)
    {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getPassword());
        var auth = this.authManager.authenticate(usernamePassword);
        var token = this.authorizationService.generateToken((User)auth.getPrincipal());

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity register(@RequestBody RegisterDTO dto)
    {
        if(this.userRepository.findByLogin(dto.getLogin()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.getPassword());
        User user = new User(dto.getLogin(), encryptedPassword, dto.getRole());

        this.userRepository.save(user);

        return ResponseEntity.ok().build();
    }
}
