package com.superfintech.superpayment.entity;

import com.superfintech.superpayment.entity.status.EnumPaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@EqualsAndHashCode
@Table(indexes = {@Index(name = "idx_voucher", columnList = "voucher")},
        uniqueConstraints = {@UniqueConstraint(columnNames = {"voucher", "code", "companyId"})})
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
