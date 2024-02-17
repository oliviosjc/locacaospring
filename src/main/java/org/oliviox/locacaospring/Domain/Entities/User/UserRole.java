package org.oliviox.locacaospring.Domain.Entities.User;

public enum UserRole
{
    MASTER("master"),
    RENTAL_ADMIN("rental_admin");

    private String role;

    UserRole(String role)
    {
        this.role = role;
    }

    public String getRole()
    {
        return this.role;
    }
}
