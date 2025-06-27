package com.practice.springboot.request;

import com.practice.springboot.card.entity.CardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardRequest {
    private String cvv;

    private String cardNo;

    private double balance;

}
