package org.oliviox.locacaospring.Domain.Entities.Contract;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.oliviox.locacaospring.Domain.Entities.Base.BaseEntity;
import org.oliviox.locacaospring.Domain.Entities.Machine.MachineUnit;

import java.math.BigDecimal;

@Entity
@Table(name = "TB_RENTALCONTRACTMACHINEHISTORIES")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalContractMachineUnitHistory extends BaseEntity
{
    @ManyToOne
    @JoinColumn(name = "machineUnitId")
    private MachineUnit machineUnit;

    @ManyToOne
    @JoinColumn(name = "rentalContractId")
    private RentalContract rentalContract;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "totalAmountReceived", nullable = false)
    private BigDecimal totalAmountReceived;

    @Column(name = "active", nullable = false)
    private boolean active;
}
