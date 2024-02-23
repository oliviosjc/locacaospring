package org.oliviox.locacaospring.Domain.Entities.Customer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.oliviox.locacaospring.Domain.Entities.Base.BaseEntity;
import org.oliviox.locacaospring.Domain.Entities.Contract.RentalContract;
import org.oliviox.locacaospring.Domain.Entities.Machine.Machine;

import java.util.List;

@Entity
@Table(name = "TB_CUSTOMERS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntity
{
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "cellPhone", unique = true, length = 11,nullable = false)
    private String cellphone;

    @Column(name = "document", unique = true, length = 14,nullable = false)
    private String document;

    @Column(name = "cep", length = 8)
    private String cep;

    @Column(name = "street")
    private String street;

    @Column(name = "number", length = 10)
    private String number;

    @Column(name = "complement")
    private String complement;

    @Column(name = "neighborhood")
    private String neighborhood;

    @Column(name = "corporateName")
    private String corporateName;

    @Column(name = "fantasyName")
    private String fantasyName;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY)
    private List<RentalContract> rentalContracts;
}
