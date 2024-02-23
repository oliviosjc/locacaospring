package org.oliviox.locacaospring.Domain.Entities.Machine;

import jakarta.persistence.*;
import lombok.*;
import org.oliviox.locacaospring.Domain.Entities.Base.BaseEntity;
import org.oliviox.locacaospring.Domain.Entities.Contract.RentalContract;
import org.oliviox.locacaospring.Domain.Entities.Contract.RentalContractMachineUnitHistory;
import org.oliviox.locacaospring.Domain.Entities.User.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "TB_MACHINEUNITS")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineUnit extends BaseEntity
{
    @Column(name = "purchasePrice", precision = 10, scale = 2, nullable = false)
    private BigDecimal purchasePrice;

    @Column(name = "purchaseDate")
    private LocalDate purchaseDate;

    @Column(name = "averageCostsPerMaintenance", precision = 10, scale = 2, nullable = false)
    private BigDecimal averageCostsPerMaintenance;

    @Column(name = "averageMaintenanceDays", precision = 10, scale = 2, nullable = false)
    private BigDecimal averageMaintenanceDays;

    @Column(name = "amountCostsPerMaintenance", precision = 10, scale = 2, nullable = false)
    private BigDecimal amountCostsPerMaintenance;

    @ManyToOne
    @JoinColumn(name = "machineId")
    private Machine machine;

    @OneToMany(mappedBy = "machineUnit", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MachineUnitMaintenance> unitMaintenances;

    @Column(name = "maintenancesQuantity", nullable = false)
    private Integer maintenancesQuantity;

    @Column(name = "externalIdentifier", unique = true, nullable = false, length = 255, columnDefinition = "VARCHAR(255) DEFAULT 'default_value'")
    private String externalIdentifier;

    @Column(name = "principalColor", nullable = true)
    private String principalColor;

    @Column(name = "secondaryColor", nullable = true)
    private String secondaryColor;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "machineUnit", fetch = FetchType.LAZY)
    private List<RentalContractMachineUnitHistory> rentalContractMachineUnitHistories;

    public MachineUnit(BigDecimal purchasePrice,
                       LocalDate purchaseDate,
                       String externalIdentifier,
                       String principalColor,
                       String secondaryColor,
                       String name,
                       User user)
    {
        this.setPrincipalColor(principalColor);
        this.setSecondaryColor(secondaryColor);
        this.setMachine(machine);
        this.setPurchasePrice(purchasePrice);
        this.setPurchaseDate(purchaseDate);
        this.setAverageCostsPerMaintenance(new BigDecimal(0));
        this.setAverageMaintenanceDays(new BigDecimal(0));
        this.setAmountCostsPerMaintenance(new BigDecimal(0));
        this.setMaintenancesQuantity(0);
        this.setUser(user);
        this.setExternalIdentifier(externalIdentifier);
        this.setName(name);
    }

    public void add(MachineUnitMaintenance machineUnitMaintenance)
    {
        machineUnitMaintenance.setMachineUnit(this);
        this.unitMaintenances.add(machineUnitMaintenance);
        this.maintenancesQuantity++;
        updateAveragesAndAmounts();
    }

    public void delete(MachineUnitMaintenance machineUnitMaintenance)
    {
        this.unitMaintenances.remove(machineUnitMaintenance);
        this.maintenancesQuantity--;
        updateAveragesAndAmounts();
    }

    private void updateAveragesAndAmounts()
    {
        BigDecimal totalMaintenanceCost = BigDecimal.ZERO;
        BigDecimal totalMaintenanceDays = BigDecimal.ZERO;

        for (MachineUnitMaintenance maintenance : this.unitMaintenances)
        {
            totalMaintenanceCost = totalMaintenanceCost.add(maintenance.getValue());
            long days = java.time.temporal.ChronoUnit.DAYS.between(maintenance.getEntryDate(), maintenance.getExitDate());
            totalMaintenanceDays = totalMaintenanceDays.add(BigDecimal.valueOf(days));
        }

        if (this.maintenancesQuantity > 0)
        {
            this.averageCostsPerMaintenance = totalMaintenanceCost.divide(BigDecimal.valueOf(this.maintenancesQuantity), 2, RoundingMode.HALF_UP);
            this.amountCostsPerMaintenance = totalMaintenanceCost;
            this.averageMaintenanceDays = totalMaintenanceDays.divide(BigDecimal.valueOf(this.maintenancesQuantity), 2, RoundingMode.HALF_UP);
        } else
        {
            this.amountCostsPerMaintenance = BigDecimal.ZERO;
            this.averageCostsPerMaintenance = BigDecimal.ZERO;
            this.averageMaintenanceDays = BigDecimal.ZERO;
        }

        if (this.machine != null)
        {
            this.machine.handleMaintenanceAdded(totalMaintenanceCost);
        }
    }
}