package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sauravi Thapa 10/13/19
 */
@Entity
@Table(name = "unit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Unit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code",nullable = false, length = 50)
    private String code;

    @Column(name = "status")
    private Character status;

    @Column(name = "remarks")
    private String remarks;

}
