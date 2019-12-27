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
@Table(name = "opd_billing_cheque")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OPDBillingCheque implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    private List<OPDBillingService> opdBillingService;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private RegisteredBank bank;

    @Column(name = "accountNumber")
    private String accountNumber;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "status")
    private Character status;


}
