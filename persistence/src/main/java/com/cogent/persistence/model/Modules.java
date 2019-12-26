package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Rupak
 */

@Table(name = "modules")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Modules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "module_url")
    private String moduleUrl;

    @Column(name = "status")
    private Character status;




}
