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
@Table(name = "opd_billing_credit_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OPDBillingCreditDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    private List<OPDBillingService> opdBillingService;

    @Column(name = "authorized_by")
    private String authorizedBy;

    @Column(name = "credit_details")
    private String creditDetails;

    @Column(name = "status")
    private Character status;

    @Column(name = "remarks")
    private String remarks;

}
