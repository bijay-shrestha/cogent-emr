package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author smriti on 2019-10-14
 */
@Entity
@Table(name = "appointment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*eg. InPatient, New Patient*/
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_type_id")
    private PatientType patientTypeId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patientId;

    /*eg. Doctor, Department, Package*/
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_type_id")
    private AppointmentType appointmentTypeId;

    /*eg. Walk in, Phone, Online*/
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment_mode_id")
    private AppointmentMode appointmentModeId;

    /*eg.Doctor name like Dr.Sanjeev Uprety*/
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctorId;

    /*eg.Doctor name like Dr.Sanjeev Uprety*/
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "specialization_id")
    private Specialization specializationId;

    /*payable or non-payable*/
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_type")
    private BillType billType;

    @Temporal(TemporalType.DATE)
    @Column(name = "appointment_date")
    private Date appointmentDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Date endTime;

    @Column(name = "appointment_number", updatable = false)
    private String appointmentNumber;

    @Column(name = "unique_id")
    private String uniqueId;

    /* /*I = Incomplete
     * C = Cancel
     * S= Success
     */
    @Column(name = "status")
    private Character status;

    @Column(name = "reason")
    private String reason;

    @Column(name = "emergency")
    private Character emergency;

    @Column(name = "referred_by")
    private String referredBy;

    /*If cancel the appointment, cancellation remarks is must*/
    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_date_nepali")
    private String createdDateNepali;

    @Column(name = "send_email")
    private Character sendEmail;
}
