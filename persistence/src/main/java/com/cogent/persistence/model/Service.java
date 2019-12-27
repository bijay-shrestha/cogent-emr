package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sauravi Thapa 11/15/19
 * This entity is uded to store the detail about the service provided by the hospital
 * Eg : Blood test,Radiology
 */

@Entity
@Table(name = "service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Service implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subDepartment_id")
    private SubDepartment subDepartment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "serviceType_id")
    private ServiceType serviceType;

    @Column(name = "status")
    private Character status;

    @Column(name = "remarks")
    private String remarks;
}
