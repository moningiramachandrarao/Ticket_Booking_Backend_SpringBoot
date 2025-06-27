package com.practice.springboot.payment.service;

import com.practice.springboot.bus.entity.BusEntity;
import com.practice.springboot.bus.repostiory.BusRepository;
import com.practice.springboot.card.entity.CardEntity;
import com.practice.springboot.card.repository.CardRepository;
import com.practice.springboot.payment.entity.PaymentEntity;
import com.practice.springboot.payment.repository.PaymentRepository;
import com.practice.springboot.paymentStatus.PaymentStatus;
import com.practice.springboot.ticket.entity.TicketEntity;
import com.practice.springboot.ticket.repository.TicketRepository;
import com.practice.springboot.ticket.service.TicketService;
import com.practice.springboot.ticketStatus.TicketStatus;
import com.practice.springboot.user.entity.UserEntity;
import com.practice.springboot.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final TicketService ticketService;
    private final PaymentRepository paymentRepository;
    private final TicketRepository ticketRepository;

    public CompletableFuture<List<Object>> payment(UUID ticketId){
      return ticketService.getById(ticketId).thenApply(ticketEntity ->{
          UserEntity userEntity = userRepository.fetchById(ticketEntity.getUserId());
          CardEntity cardEntity = userEntity.getCardEntity();
          List<Object> result = new ArrayList<>();
          if(ticketEntity.getAmount()>cardEntity.getBalance()){
              PaymentEntity paymentEntity =  paymentRepository.save(PaymentEntity.builder()
                      .paymentStatus(PaymentStatus.FAIL)
                      .build());
              result.add(ticketEntity);
              result.add(paymentEntity);
              return result;
          }
          cardEntity.setBalance(cardEntity.getBalance()-ticketEntity.getAmount());
          userEntity.setCardEntity(cardRepository.save(cardEntity));
          userRepository.save(userEntity);
          ticketEntity.setStatus(TicketStatus.PAID);

          PaymentEntity paymentEntity = paymentRepository.save(PaymentEntity.builder()
                  .paymentStatus(PaymentStatus.SUCCESS)
                  .build());
          result.add(ticketRepository.save(ticketEntity));
          result.add(paymentEntity);
          return result;
      });
    }
}
