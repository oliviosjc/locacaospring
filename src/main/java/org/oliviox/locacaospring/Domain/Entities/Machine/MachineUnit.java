package org.oliviox.locacaospring.Domain.Entities.Machine;

import jakarta.persistence.*;
import lombok.*;
import org.oliviox.locacaospring.Domain.Entities.Base.BaseEntity;

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

    @Column(name = "averageMaintenanceCost", precision = 10, scale = 2, nullable = false)
    private BigDecimal averageMaintenanceCost;

    @ManyToOne
    @JoinColumn(name = "machineId")
    private Machine machine;

    @OneToMany(mappedBy = "machineUnit", fetch = FetchType.LAZY)
    private List<MachineUnitMaintenance> unitMaintenances;

    @Column(name = "maintenancesQuantity", nullable = false)
    private Integer maintenancesQuantity;

    public MachineUnit(BigDecimal purchasePrice, LocalDate purchaseDate)
    {
        this.setMachine(machine);
        this.setPurchasePrice(purchasePrice);
        this.setPurchaseDate(purchaseDate);
        this.setAverageMaintenanceCost(new BigDecimal(0));
        this.setMaintenancesQuantity(0);
    }

    public void add(MachineUnitMaintenance machineUnitMaintenance)
    {
        machineUnitMaintenance.setMachineUnit(this);
        this.unitMaintenances.add(machineUnitMaintenance);
        this.maintenancesQuantity++;

        BigDecimal totalMaintenanceCost = BigDecimal.ZERO;
        for (MachineUnitMaintenance maintenance : this.unitMaintenances)
        {
            totalMaintenanceCost = totalMaintenanceCost.add(maintenance.getValue());
        }

        this.averageMaintenanceCost = totalMaintenanceCost.divide(BigDecimal.valueOf(this.maintenancesQuantity), 2, RoundingMode.HALF_UP);
        if (this.machine != null)
        {
            this.machine.handleMaintenanceAdded(totalMaintenanceCost);
        }
    }
}