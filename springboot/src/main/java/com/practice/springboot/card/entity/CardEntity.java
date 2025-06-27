package com.practice.springboot.card.entity;

import com.practice.springboot.request.CardRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardEntity {

    @Id
    @GeneratedValue
    private UUID cardId;

    private String cvv;

    private String cardNo;

    private double balance;

    public static CardEntity toEntity(CardRequest cardRequest){
        return CardEntity.builder()
                .cvv(cardRequest.getCvv())
                .balance(cardRequest.getBalance())
                .cardNo(cardRequest.getCardNo())
                .build();
    }
}
