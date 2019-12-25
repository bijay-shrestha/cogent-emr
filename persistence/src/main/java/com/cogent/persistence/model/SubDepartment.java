package com.cogent.persistence.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sub_department")
@NoArgsConstructor
@Getter
@Setter
public class SubDepartment{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "code", nullable = false, length = 50, updatable = false)
    private String code;

    @Column(name = "status")
    private Character status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id",updatable = false)
    private Department department;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "last_modified_date")
    private Date lastModifiedDate;

    @Column(name = "created_by_id")
    private Long createdById;

    @Column(name = "last_modified_by_id")
    private Long modifiedById;
}