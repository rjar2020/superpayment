package com.superfintech.superpayment.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Data
@Entity
@EqualsAndHashCode
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name = "uuid")
    private String id;
    private String name;

    @ElementCollection(targetClass = Payment.class, fetch = FetchType.EAGER)
    List<Payment> payments;

    private boolean deleted;
}
