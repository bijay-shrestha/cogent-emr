package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author smriti on 2019-10-18
 */
@Entity
@Table(name = "doctor_department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDepartment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "doctor_id")
    private Long doctor;

    @Column(name = "department_id")
    private Long department;

    @Column(name = "status")
    private Character status;
}
