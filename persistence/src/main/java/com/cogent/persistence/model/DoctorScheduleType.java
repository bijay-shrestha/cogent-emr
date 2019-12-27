package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author smriti on 12/11/2019
 */
@Table(name = "doctor_schedule_type")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorScheduleType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "doctor_type_id")
    private Long doctorTypeId;

    @Column(name = "status")
    private Character status;
}
