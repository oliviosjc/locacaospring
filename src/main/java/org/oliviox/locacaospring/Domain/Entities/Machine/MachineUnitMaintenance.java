package org.oliviox.locacaospring.Domain.Entities.Machine;

import jakarta.persistence.*;
import lombok.*;
import org.oliviox.locacaospring.Domain.Entities.Base.BaseEntity;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "TB_MACHINEUNITMAINTENANCES")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MachineUnitMaintenance extends BaseEntity
{
    @Column(name = "value", precision = 10, scale = 2, nullable = false)
    private BigDecimal value;

    @Column(name = "entryDate", nullable = false)
    private LocalDate entryDate;

    @Column(name = "exitDate", nullable = false)
    private LocalDate exitDate;

    @Column(name = "maintenanceDescription", length = 255, nullable = true)
    private String maintenanceDescription;

    @ManyToOne
    @JoinColumn(name = "machineUnitId")
    private MachineUnit machineUnit;

    public MachineUnitMaintenance(BigDecimal value, LocalDate entryDate, LocalDate exitDate, String maintenanceDescription)
    {
        this.value = value;
        this.entryDate = entryDate;
        this.exitDate = exitDate;
        this.maintenanceDescription = maintenanceDescription;
    }
}
