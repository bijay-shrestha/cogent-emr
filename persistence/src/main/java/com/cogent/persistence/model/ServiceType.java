package com.cogent.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sauravi Thapa 11/13/19
 * This entity is used to save the type of services provided by hospital
 * eg: package,test,Doctor
 */

@Entity
@Table(name = "service_type")
@NoArgsConstructor
@Getter
@Setter
public class ServiceType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "code", nullable = false, length = 50,updatable = false)
    private String code;

    @Column(name = "status")
    private Character status;

}
