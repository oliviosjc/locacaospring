package org.oliviox.locacaospring.Domain.Entities.User;

public enum UserRole
{
    MASTER("master");

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
