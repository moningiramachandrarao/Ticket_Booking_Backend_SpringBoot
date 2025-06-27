package com.practice.springboot.payment.repository;

import com.practice.springboot.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PaymentRepository  extends JpaRepository<PaymentEntity, UUID> {
}
