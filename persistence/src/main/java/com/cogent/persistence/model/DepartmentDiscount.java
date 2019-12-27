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
@Table(name = "department_discount")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDiscount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discountscheme_id")
    private DiscountScheme discountScheme;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Column(name = "discount_percentage", nullable = false, length = 50)
    private Double discount;

    @Column(name = "status")
    private Character status;

    @Column(name = "remarks")
    private String remarks;
}
