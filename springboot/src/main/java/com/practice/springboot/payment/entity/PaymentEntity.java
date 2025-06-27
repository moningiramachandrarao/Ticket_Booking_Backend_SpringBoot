package com.practice.springboot.payment.entity;

import com.practice.springboot.paymentStatus.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Table(name="payment")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {

    @Id
    @GeneratedValue
    private UUID paymentId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
}
