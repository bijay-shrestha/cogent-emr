package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sauravi Thapa 12/11/19
 */

@Entity
@Table(name = "payment_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "payment_mode")
    private String paymentMode;

    @Column(name = "status")
    private Character status;

    @Column(name="reamrks")
    private String remarks;
}
