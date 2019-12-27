package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author smriti on 2019-11-04
 */
@Entity
@Table(name = "follow_up_setup")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FollowUpSetup implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "follow_up_intervals")
    private Integer followUpIntervals;

    @Column(name = "number_of_follow_ups")
    private Integer numberOfFollowUps;

    @Column(name = "follow_up_applicable")
    private Character followUpApplicable;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_type")
    private PatientType patientType;

    @Column(name = "status")
    private Character status;

    @Column(name = "remarks")
    private String remarks;
}
