package org.oliviox.locacaospring.Domain.Entities.Machine;

import jakarta.persistence.*;
import lombok.*;
import org.oliviox.locacaospring.Domain.Entities.Base.BaseEntity;
import org.oliviox.locacaospring.Domain.Entities.Brand.Brand;
import org.oliviox.locacaospring.Domain.Entities.User.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "machine", fetch = FetchType.LAZY)
    private List<MachineUnit> machineUnits;

    @Column(name = "averagePurchasePrice", precision = 10, scale = 2, nullable = false)
    private BigDecimal averagePurchisePrice;

    @Column(name = "averageMaintenanceCosts", precision = 10, scale = 2, nullable = false)
    private BigDecimal averageMaintenanceCost;

    @Column(name = "averageMaintenanceDays", precision = 10, scale = 2, nullable = false)
    private BigDecimal averageMaintenanceDays;

    @Column(name = "unitsQuantity", nullable = false)
    private Integer unitsQuantity;

    public Machine(String name, Brand brand)
    {
        this.setName(name);
        this.setBrand(brand);
        this.setAverageMaintenanceCost(new BigDecimal(0));
        this.setAveragePurchisePrice(new BigDecimal(0));
        this.setUnitsQuantity(0);
    }

    public void add(MachineUnit machineUnit)
    {
        machineUnit.setMachine(this);
        this.machineUnits.add(machineUnit);
        this.unitsQuantity++;
        BigDecimal totalPurchasePrice = this.averagePurchisePrice.multiply(BigDecimal.valueOf(this.unitsQuantity - 1));
        totalPurchasePrice = totalPurchasePrice.add(machineUnit.getPurchasePrice());
        this.averagePurchisePrice = totalPurchasePrice.divide(BigDecimal.valueOf(this.unitsQuantity), 2, RoundingMode.HALF_UP);
    }

    public void handleMaintenanceAdded(BigDecimal totalMaintenanceCost)
    {
        this.updateAverageMaintenanceCost(totalMaintenanceCost);
    }

    private void updateAverageMaintenanceCost(BigDecimal totalMaintenanceCost)
    {
        int totalMaintenanceQuantity = 0;
        for (MachineUnit machineUnit : this.machineUnits)
        {
            totalMaintenanceQuantity += machineUnit.getMaintenancesQuantity();
        }

        BigDecimal newAverageMaintenanceCost;
        if (totalMaintenanceQuantity > 0)
        {
            newAverageMaintenanceCost = totalMaintenanceCost.divide(BigDecimal.valueOf(totalMaintenanceQuantity), 2, RoundingMode.HALF_UP);
        } else
        {
            newAverageMaintenanceCost = BigDecimal.ZERO;
        }

        if (!this.averageMaintenanceCost.equals(newAverageMaintenanceCost))
        {
            this.averageMaintenanceCost = newAverageMaintenanceCost;
        }
    }
}

