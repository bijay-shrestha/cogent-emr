package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author smriti ON 24/12/2019
 */
@Table(name = "application_module")
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationModule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_department_id")
    private SubDepartment subDepartmentId;

    @Column(name = "status")
    private Character status;

    @Column(name = "remarks")
    private String remarks;
}
