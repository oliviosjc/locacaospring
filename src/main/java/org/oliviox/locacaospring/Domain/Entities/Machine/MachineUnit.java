package org.oliviox.locacaospring.Domain.Entities.Machine;

import jakarta.persistence.*;
import lombok.*;
import org.oliviox.locacaospring.Domain.Entities.Base.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "TB_MACHINEUNITS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineUnit extends BaseEntity
{
    @Column(name = "purchasePrice", precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "purchaseDate")
    private LocalDate purchaseDate;

    @ManyToOne
    @JoinColumn(name = "machineId")
    private Machine machine;
}
