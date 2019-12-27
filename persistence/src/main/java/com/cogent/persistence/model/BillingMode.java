package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sauravi Thapa 12/4/19
 * This entity is used to store the various mode of billing used in hospital.
 * Thus, the rate of service provided by hosital varies according to the billing mode.
 *  * eg: cost of blood test in general mode =250
 *        cost of blood test in elderly mode =150
 *
 *
 */


@Entity
@Table(name = "billing_mode")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillingMode implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Character status;

    @Column(name = "remarks")
    private String remarks;
}
