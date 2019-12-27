package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Rupak
 */

@Entity
@Table(name = "hospital")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hospital implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "hospital_address")
    private String hospitalAddress;

    @Column(name = "hospital_pan_number")
    private String hospitalPanNumber;

    @Column(name = "hospital_phone")
    private String hospitalPhone;

    @Column(name = "hospital_logo")
    private String hospitalLogo;

    @Column(name = "status")
    private Character status;

    @Column(name = "remarks")
    private String remarks;


}
