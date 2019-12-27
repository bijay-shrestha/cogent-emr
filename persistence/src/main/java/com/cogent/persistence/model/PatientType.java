package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author smriti on 2019-09-26
 * eg. Inpatient, out patient
 */
@Entity
@Table(name = "patient_type")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    //todo: code = IP for Inpatient and OP for Outpatient
    @Column(name = "code")
    private String code;

    @Column(name = "status")
    private Character status;

    @Column(name = "remarks")
    private String remarks;
}
