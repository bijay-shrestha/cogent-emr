package com.cogent.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "department")
@NoArgsConstructor
@Getter
@Setter
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "department_name", nullable = false, length = 50)
    private String name;

    @Column(name = "code", nullable = false, length = 50,updatable = false)
    private String code;

    @Column(name = "status")
    private Character status;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @Column(name = "created_by_id")
    private Long createdById;

    @Column(name = "last_modified_by_id")
    private Long modifiedById;

    @Column(name = "remarks")
    private String remarks;
}
