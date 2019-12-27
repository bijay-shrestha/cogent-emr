package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author smriti on 2019-08-11
 */
@Entity
@Table(name = "admin_category")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "code", length = 10)
    private String code;

    @Column(name = "designation", length = 50)
    private String designation;

    @Column(name = "registration_number", length = 50)
    private String registrationNumber;

    @Column(name = "status")
    private Character status;

    @Column(name = "remarks", length = 1000)
    private String remarks;
}
