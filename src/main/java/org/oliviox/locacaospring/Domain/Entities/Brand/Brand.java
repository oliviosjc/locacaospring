package org.oliviox.locacaospring.Domain.Entities.Brand;

import jakarta.persistence.*;
import lombok.*;
import org.oliviox.locacaospring.Domain.Entities.Base.BaseEntity;
import org.oliviox.locacaospring.Domain.Entities.Machine.Machine;
import org.oliviox.locacaospring.Domain.Entities.User.User;

import java.util.List;

@Entity
@Table(name = "TB_BRANDS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Brand extends BaseEntity
{
    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    private List<Machine> machines;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Brand(String name, User user)
    {
        this.setUser(user);
        this.setName(name);
    }
}