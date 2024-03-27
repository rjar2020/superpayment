package com.superfintech.superpayment.repository;

import com.superfintech.superpayment.entity.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, String>, PagingAndSortingRepository<Payment, String> {

}
