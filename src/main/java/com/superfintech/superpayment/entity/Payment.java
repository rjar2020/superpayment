package com.superfintech.superpayment.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@EqualsAndHashCode
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "uuid")
    private String id;

    private String companyId;

    @ElementCollection(targetClass = Voucher.class, fetch = FetchType.EAGER)
    private List<Voucher> vouchers;

    private BigDecimal originalAmount;

    private BigDecimal paidAmount;

    private boolean paid;

    private boolean deleted;

}
