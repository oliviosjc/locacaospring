package org.oliviox.locacaospring.Domain.Entities.Brand;

import jakarta.persistence.*;
import lombok.*;
import org.oliviox.locacaospring.Domain.Entities.Base.BaseEntity;
import org.oliviox.locacaospring.Domain.Entities.Machine.Machine;

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

    public Brand(String name)
    {
        this.setName(name);
    }
}