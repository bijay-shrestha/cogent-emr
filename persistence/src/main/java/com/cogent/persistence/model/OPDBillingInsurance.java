package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sauravi Thapa 12/11/19
 */

@Entity
@Table(name = "opd_billing_insurance")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OPDBillingInsurance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    private List<OPDBillingService> opdBillingService;

    @Column(name = "claim_code")
    private String claimCode;

    @Column(name = "amount")
    private String amount;

    @Column(name = "status")
    private Character status;
}


