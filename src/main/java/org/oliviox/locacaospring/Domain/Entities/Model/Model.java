package org.oliviox.locacaospring.Domain.Entities.Model;

import jakarta.persistence.*;
import lombok.*;
import org.oliviox.locacaospring.Domain.Entities.Base.BaseEntity;
import org.oliviox.locacaospring.Domain.Entities.Brand.Brand;
import org.oliviox.locacaospring.Domain.Entities.Machine.Machine;
import org.oliviox.locacaospring.Domain.Entities.User.User;

import java.util.List;

@Entity
@Table(name = "TB_MODELS")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Model  extends BaseEntity
{
    @OneToMany(mappedBy = "model", fetch = FetchType.LAZY)
    private List<Machine> machines;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public Model(String name)
    {
        this.setName(name);
    }
}
