package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sauravi Thapa 10/2/19
 */
@Entity
@Table(name = "ward")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ward implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "status")
    private Character status;

    @Column(name = "has_Unit")
    private Boolean has_Unit;

    @Column(name = "remarks")
    private String remarks;
}
