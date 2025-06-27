package com.practice.springboot.payment.controller;

import com.practice.springboot.payment.entity.PaymentEntity;
import com.practice.springboot.payment.service.PaymentService;
import com.practice.springboot.paymentStatus.PaymentStatus;
import com.practice.springboot.ticket.entity.TicketEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PatchMapping("/ticket/{ticket_id}")
    public CompletableFuture<ResponseEntity<Map<String,Object>>>  payment(@PathVariable UUID ticket_id){
        return paymentService.payment(ticket_id).thenApply(result -> {
            Map<String,Object> response = new HashMap<>();
            response.put("Ticket Details",(TicketEntity)result.get(0));
            PaymentEntity paymentEntity = (PaymentEntity) result.get(1);
            if(paymentEntity.getPaymentStatus().equals(PaymentStatus.SUCCESS)){
                response.put("Message","Payment Successfully completed");
            }
            else{
                response.put("Message","Insufficient amount");
            }
            response.put("Payment Details",paymentEntity);
            response.put("Status", HttpStatus.OK);
            return new ResponseEntity<>(response,HttpStatus.OK);
        });
    }

}
