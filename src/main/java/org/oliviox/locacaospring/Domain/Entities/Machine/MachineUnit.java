package org.oliviox.locacaospring.Domain.Entities.Machine;

import jakarta.persistence.*;
import lombok.*;
import org.oliviox.locacaospring.Domain.Entities.Base.BaseEntity;
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

    @Column(name = "averageMaintenanceCost", precision = 10, scale = 2, nullable = false)
    private BigDecimal averageMaintenanceCost;

    @Column(name = "averageMaintenanceDays", precision = 10, scale = 2, nullable = false)
    private BigDecimal averageMaintenanceDays;

    @ManyToOne
    @JoinColumn(name = "machineId")
    private Machine machine;

    @OneToMany(mappedBy = "machineUnit", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MachineUnitMaintenance> unitMaintenances;

    @Column(name = "maintenancesQuantity", nullable = false)
    private Integer maintenancesQuantity;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    public MachineUnit(BigDecimal purchasePrice, LocalDate purchaseDate, User user)
    {
        this.setMachine(machine);
        this.setPurchasePrice(purchasePrice);
        this.setPurchaseDate(purchaseDate);
        this.setAverageMaintenanceCost(new BigDecimal(0));
        this.setAverageMaintenanceDays(new BigDecimal(0));
        this.setMaintenancesQuantity(0);
        this.user = user;
        this.setName("");
    }

    public void add(MachineUnitMaintenance machineUnitMaintenance)
    {
        machineUnitMaintenance.setMachineUnit(this);
        this.unitMaintenances.add(machineUnitMaintenance);
        this.maintenancesQuantity++;
        updateAverages();
    }

    public void delete(MachineUnitMaintenance machineUnitMaintenance)
    {
        this.unitMaintenances.remove(machineUnitMaintenance);
        this.maintenancesQuantity--;
        updateAverages();
    }

    private void updateAverages()
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
            this.averageMaintenanceCost = totalMaintenanceCost.divide(BigDecimal.valueOf(this.maintenancesQuantity), 2, RoundingMode.HALF_UP);
            this.averageMaintenanceDays = totalMaintenanceDays.divide(BigDecimal.valueOf(this.maintenancesQuantity), 2, RoundingMode.HALF_UP);
        } else
        {
            this.averageMaintenanceCost = BigDecimal.ZERO;
            this.averageMaintenanceDays = BigDecimal.ZERO;
        }

        if (this.machine != null)
        {
            this.machine.handleMaintenanceAdded(totalMaintenanceCost);
        }
    }
}