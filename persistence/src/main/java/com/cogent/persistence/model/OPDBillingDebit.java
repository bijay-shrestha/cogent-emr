package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sauravi Thapa 12/10/19
 */

@Entity
@Table(name = "opd_billing_debit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OPDBillingDebit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    private List<OPDBillingService> opdBillingService;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private RegisteredBank bank;

    @Column(name = "atm_number")
    private String atmNumber;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "status")
    private Character status;

}
