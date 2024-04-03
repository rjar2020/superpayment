package com.superfintech.superpayment.entity;

import com.superfintech.superpayment.entity.status.EnumPaymentStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
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

    private boolean deleted;

    @CreatedDate
    private LocalDateTime insertTimestamp;

    @LastModifiedDate
    private LocalDateTime lastUpdateTimestamp;

}
