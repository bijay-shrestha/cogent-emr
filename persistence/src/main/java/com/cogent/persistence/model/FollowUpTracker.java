package com.cogent.persistence.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author smriti on 18/11/2019
 */
@Entity
@Table(name = "follow_up_tracker")
@Getter
@Setter
public class FollowUpTracker implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_type_id")
    private PatientType patientTypeId;

    @Column(name = "parent_appointment_number")
    private String parentAppointmentNumber;

    @Column(name = "remaining_number_of_follow_ups")
    private Integer remainingNumberOfFollowUps;

    /*appointment date for outpatient
    and discharge date for inpatient */
    @Temporal(TemporalType.DATE)
    @Column(name = "completed_date")
    private Date completedDate;

    @Column(name = "active")
    private Character active;

    /*I = Incomplete
     * C = Cancel
     * S= Success*/
    @Column(name = "appointment_status")
    private Character appointmentStatus;
}
