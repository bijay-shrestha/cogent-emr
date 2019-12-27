package com.cogent.persistence.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Rupak
 */

@Entity
@Table(name = "opd_billing")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OPDBilling implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "invoice_date")
    private Date invoiceDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_mode_id")
    private BillingMode billingMode;

    @Column(name = "remarks")
    private String remarks;

    @Column(name = "gross_invoice_amount")
    private Double grossInvoiceAmount;

    @Column(name = "invoice_discount_amount")
    private Double invoiceDiscountAmount;

    @Column(name = "total_vat_amount")
    private Double totalVatAmount;

    @Column(name = "net_invoice_amount")
    private Double netInvoiceAmount;

    @Column(name = "is_refund")
    private boolean isRefund;

    @Column(name = "is_cancel")
    private boolean isCancel;

    @Column(name = "has_credit_details")
    private boolean hasCreditDetails;

    @Column(name = "fiscal_year")
    private String fiscalYear;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private Character status;

    @Column(name = "has_insurance")
    private boolean hasInsurance;

    @Column(name = "is_original")
    private boolean isOriginal;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_scheme_id")
    private DiscountScheme discountScheme;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_type_id")
    private PaymentType paymentType;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "referrer_id")
    private Referrer referrer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "appointment")
    private Appointment appointment;

    @OneToMany(fetch = FetchType.LAZY)
    private List<OPDBillingCreditDetails> opdBillingCreditDetails;

}
