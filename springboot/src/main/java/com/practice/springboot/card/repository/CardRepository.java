package com.practice.springboot.card.repository;

import com.practice.springboot.card.entity.CardEntity;
import com.practice.springboot.exceptions.MisMatchException;
import com.practice.springboot.exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, UUID> {

    default CardEntity fetchById(UUID cardId){
        return findById(cardId).orElseThrow(()->new NotFoundException("CardId Not Found"));
    }
}
