package com.cogent.persistence.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author smriti ON 25/12/2019
 */
@Table(name = "admin_application_module")
@Entity
@Getter
@Setter
public class AdminApplicationModule implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "admin_id")
    private Long adminId;

    @Column(name = "application_module_id")
    private Long applicationModuleId;

    @Column(name = "status")
    private Character status;
}
