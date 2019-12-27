package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sauravi Thapa 11/11/19
 */

@Entity
@Table(name = "discount_scheme")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiscountScheme implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "code", nullable = false, length = 50)
    private String code;

    @Column(name = "total_discount_percentage")
    private Double netDiscount;

    @Column(name = "status")
    private Character status;

    @Column(name = "has_netDiscount")
    private Boolean has_netDiscount;

    @Column(name = "remarks")
    private String remarks;
}
