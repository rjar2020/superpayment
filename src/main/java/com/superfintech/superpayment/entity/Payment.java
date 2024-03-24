package com.superfintech.superpayment.entity;

import com.superfintech.superpayment.entity.status.EnumPaymentStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;

@Data
@Builder
@Entity
@EqualsAndHashCode
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "uuid")
    private String id;

    private String companyId;

    private String code;

    private String voucher;

    private BigDecimal orderAmount;

    private EnumPaymentStatus status;

    @Version
    private int version;

}
