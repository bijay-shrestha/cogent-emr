package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sauravi Thapa 12/10/19
 * This entity is used to store registered bank info of nepal
 */

@Entity
@Table(name = "registered_bank")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisteredBank implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "contact")
    private String contact;

    @Column(name = "address")
    private String address;

    @Column(name = "swift_code")
    private String swiftCode;

    @Column(name = "status")
    private Character status;

    @Column(name = "remarks")
    private String remarks;

}
