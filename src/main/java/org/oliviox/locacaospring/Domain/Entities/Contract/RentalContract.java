package org.oliviox.locacaospring.Domain.Entities.Contract;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.oliviox.locacaospring.Domain.Entities.Base.BaseEntity;
import org.oliviox.locacaospring.Domain.Entities.Customer.Customer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "TB_RENTALCONTRACTS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentalContract extends BaseEntity
{
    @ManyToOne
    @JoinColumn(name = "customer")
    private Customer customer;

    @Column(name = "startsIn", nullable = false)
    private LocalDate startsIn;

    @Column(name = "endsIn", nullable = false)
    private LocalDate endsIn;

    @Column(name = "externalIdentifier", unique = true)
    private String externalIdentifier;

    @Column(name = "macroReceivablesControl", nullable = false)
    private boolean macroReceivablesControl;

    @Column(name = "splitReceivablesEqualsPerUnits", nullable = false)
    private boolean splitReceivablesEqualsPerUnits;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "totalAmountReceived", nullable = false)
    private BigDecimal totalAmountReceived;

    @OneToMany(mappedBy = "rentalContract", fetch = FetchType.LAZY)
    private List<RentalContractMachineUnitHistory> rentalContractMachineUnitHistories;
}
