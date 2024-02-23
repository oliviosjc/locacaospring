package org.oliviox.locacaospring.Domain.Entities.Machine;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Cascade;
import org.oliviox.locacaospring.Domain.Entities.Base.BaseEntity;
import org.oliviox.locacaospring.Domain.Entities.Brand.Brand;
import org.oliviox.locacaospring.Domain.Entities.Model.Model;
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
    @JoinColumn(name = "modelId")
    private Model model;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "machine", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MachineUnit> machineUnits;

    @Column(name = "averagePurchasePrice", precision = 10, scale = 2, nullable = false)
    private BigDecimal averagePurchasePrice;

    @Column(name = "averageCostsPerMaintenance", precision = 10, scale = 2, nullable = false)
    private BigDecimal averageCostsPerMaintenance;

    @Column(name = "amountCostsPerMaintenance")
    private BigDecimal amountCostsPerMaintenance;

    @Column(name = "averageDaysPerMaintenance", precision = 10, scale = 2, nullable = false)
    private BigDecimal averageDaysPerMaintenance;

    @Column(name = "amountTotalReceived")
    private BigDecimal amountTotalReceived;

    @Column(name = "unitsQuantity", nullable = false)
    private Integer unitsQuantity;

    public Machine(String name, Brand brand, User user, Model model)
    {
        this.setName(name);
        this.setModel(model);
        this.setBrand(brand);
        this.setAveragePurchasePrice(new BigDecimal(0));
        this.setAverageCostsPerMaintenance(new BigDecimal(0));
        this.setAmountTotalReceived(new BigDecimal(0));
        this.setAmountCostsPerMaintenance(new BigDecimal(0));
        this.setAverageDaysPerMaintenance(new BigDecimal(0));
        this.setUnitsQuantity(0);
        this.setUser(user);
    }

    public void add(MachineUnit machineUnit)
    {
        machineUnit.setMachine(this);
        this.machineUnits.add(machineUnit);
        this.unitsQuantity++;
        updateAverages();
    }

    public void delete(MachineUnit machineUnit)
    {
        this.machineUnits.remove(machineUnit);
        this.unitsQuantity--;
        updateAverages();
    }

    private void updateAverages()
    {
        BigDecimal totalPurchasePrice = BigDecimal.ZERO;
        BigDecimal totalMaintenanceCost = BigDecimal.ZERO;
        BigDecimal totalMaintenanceDays = BigDecimal.ZERO;

        for (MachineUnit unit : this.machineUnits)
        {
            totalPurchasePrice = totalPurchasePrice.add(unit.getPurchasePrice());
            totalMaintenanceCost = totalMaintenanceCost.add(unit.getAverageCostsPerMaintenance().multiply(BigDecimal.valueOf(unit.getMaintenancesQuantity())));
            totalMaintenanceDays = totalMaintenanceDays.add(unit.getAverageMaintenanceDays().multiply(BigDecimal.valueOf(unit.getMaintenancesQuantity())));
        }

        if (this.unitsQuantity > 0)
        {
            this.amountCostsPerMaintenance = totalMaintenanceCost;
            this.averagePurchasePrice = totalPurchasePrice.divide(BigDecimal.valueOf(this.unitsQuantity), 2, RoundingMode.HALF_UP);
            this.averageCostsPerMaintenance = totalMaintenanceCost.divide(BigDecimal.valueOf(this.unitsQuantity), 2, RoundingMode.HALF_UP);
            this.averageDaysPerMaintenance = totalMaintenanceDays.divide(BigDecimal.valueOf(this.unitsQuantity), 2, RoundingMode.HALF_UP);
        } else
        {
            this.averagePurchasePrice = BigDecimal.ZERO;
            this.averageCostsPerMaintenance = BigDecimal.ZERO;
            this.averageDaysPerMaintenance = BigDecimal.ZERO;
        }

        updateAverageMaintenanceCost(totalMaintenanceCost);
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
        } else {
            newAverageMaintenanceCost = BigDecimal.ZERO;
        }

        if (!this.averageCostsPerMaintenance.equals(newAverageMaintenanceCost))
        {
            this.averageCostsPerMaintenance = newAverageMaintenanceCost;
        }
    }
}