package org.oliviox.locacaospring.Infraestructure.Repositories.Interfaces;

import org.oliviox.locacaospring.Domain.Entities.User.User;
import org.oliviox.locacaospring.Infraestructure.Repositories.IGenericRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface IUserRepository extends IGenericRepository<User>
{
    UserDetails findByLogin(String login);
}
