package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Sauravi Thapa 12/10/19
 */

@Entity
@Table(name = "opd_billing_credit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OPDBillingCredit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    private List<OPDBillingService> opdBillingService;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_id")
    private RegisteredBank bank;

    @Column(name = "account_number")
    private String creditNumber;

    @Column(name = "valid_upto")
    private Date validUpto;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "status")
    private Character status;


}
