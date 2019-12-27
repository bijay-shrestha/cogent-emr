package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sauravi Thapa 11/13/19
 * <p>
 * This entity is used to save charge per service
 * eg : Blood test(or any other hospital service) of Lab department cost 150
 * amount
 */

@Entity
@Table(name = "service_charge")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCharge implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private Service service;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<BillingMode> billingModes;

    @Column(name = "price")
    private Double price;

    @Column(name = "status")
    private Character status;

    @Column(name = "remarks")
    private String remarks;

}
