package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author smriti on 2019-09-27
 */
@Entity
@Table(name = "doctor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code", updatable = false)
    private String code;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column(name = "nepali_date_of_birth")
    private String nepaliDateOfBirth;

    @Column(name = "email")
    private String email;

    @Column(name = "nmc_number")
    private String nmcNumber;

    @Column(name = "joined_date")
    @Temporal(TemporalType.DATE)
    private Date joinedDate;

    @Column(name = "nepali_joined_date")
    private String nepaliJoinedDate;

    @Column(name = "resignation_date")
    @Temporal(TemporalType.DATE)
    private Date resignationDate;

    @Column(name = "nepali_resignation_date")
    private String nepaliResignationDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gender")
    private Gender gender;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country")
    private Country country;

    @Column(name = "status")
    private Character status;

    @Column(name = "remarks")
    private String remarks;
}
