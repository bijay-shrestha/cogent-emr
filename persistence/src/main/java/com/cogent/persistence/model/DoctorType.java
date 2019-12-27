package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author smriti on 08/11/2019
 * eg. Senior Doctor, Junior Doctor, Principle Doctor,
 * Registrar, Medical Officer, Duty Doctor, etc
 */
@Entity
@Table(name = "Doctor_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private Character status;
}
