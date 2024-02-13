package org.oliviox.locacaospring.Domain.Entities.Machine;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.oliviox.locacaospring.Domain.Entities.Base.BaseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class MachineUnit extends BaseEntity
{
    @Column(name = "purchasePrice", precision = 10, scale = 2)
    private BigDecimal purchasePrice;

    @Column(name = "purchaseDate")
    private LocalDate purchaseDate;

    @ManyToOne
    @JoinColumn(name = "machineId")
    private Machine machine;

    public void setPurchasePrice(BigDecimal purchasePrice)
    {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getPurchasePrice()
    {
        return this.purchasePrice;
    }

    public void setPurchaseDate(LocalDate purchaseDate)
    {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getPurchaseDate()
    {
        return this.purchaseDate;
    }
}
