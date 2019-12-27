package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sauravi Thapa 12/11/19
 */

@Entity
@Table(name = "opd_billing_service")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OPDBillingService implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "opd_billing_id")
//    private OPDBilling opdBilling;

    @Column(name = "sub_department_id")
    private SubDepartment subDepartment;

    @Column(name = "service")
    private Service service;

    @Column(name = "status")
    private Character status;

    @Column(name = "sample_id")
    private String sampleId;

}
