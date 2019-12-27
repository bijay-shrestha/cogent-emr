package com.cogent.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;


/**
 * @author Sauravi Thapa 9/24/19
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "patient")
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "surname_id")
    private Surname surname;

    @Column(name = "code", nullable = false, length = 50, updatable = false)
    private String code;

    @Column(name = "status")
    private Character status;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "gender")
    private Character gender;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "nepali_date")
    private String nepaliDateOfBirth;

    @Column(name = "emergency_contact")
    private String emergencyContact;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "citizenship_number")
    private String citizenshipNumber;

    @Column(name = "pan")
    private String pan;

    @Column(name = "email",length = 50)
    private String email;

    @Column(name = "blood_group")
    private String bloodGroup;


    @Column(name = "education")
    private String education;

    @Column(name = "qrcode")
    private String qrCode;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "his_number")
    private String hisNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "religion_id")
    private Religion religion;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marital_Status_id")
    private MaritalStatus maritalStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nationality_id")
    private Nationality nationality;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "municipality_id")
    private Municipality municipality;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "title_id")
    private Title title;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "referred_by")
    private String referredBy;
}

