package com.practice.springboot.ticket.repository;

import com.practice.springboot.exceptions.MisMatchException;
import com.practice.springboot.exceptions.NotFoundException;
import com.practice.springboot.ticket.entity.TicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<TicketEntity, UUID> {
    default TicketEntity fetchById(UUID ticketId){
        return findById(ticketId).orElseThrow(()-> new NotFoundException("Ticket Id not present"));
    }
}
