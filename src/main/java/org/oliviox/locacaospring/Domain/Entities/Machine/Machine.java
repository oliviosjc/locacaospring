package org.oliviox.locacaospring.Domain.Entities.Machine;

import jakarta.persistence.*;
import lombok.*;
import org.oliviox.locacaospring.Domain.Entities.Base.BaseEntity;
import org.oliviox.locacaospring.Domain.Entities.Brand.Brand;
import java.util.List;

@Entity
@Table(name = "TB_MACHINES")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Machine extends BaseEntity
{
    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    @OneToMany(mappedBy = "machine", fetch = FetchType.LAZY)
    private List<MachineUnit> machineUnits;

    public Machine(String name, Brand brand)
    {
        this.setName(name);
        this.setBrand(brand);
    }
}

