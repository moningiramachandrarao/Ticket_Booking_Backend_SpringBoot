package com.practice.springboot.ticket.service;

import com.practice.springboot.request.TicketRequest;
import com.practice.springboot.ticket.entity.TicketEntity;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface TicketService {
    CompletableFuture<TicketEntity> persist(TicketRequest ticketRequest, UUID busId, UUID userId);
    CompletableFuture<TicketEntity> getById(UUID ticketId);
    CompletableFuture<TicketEntity> updateTheTicketDetails(TicketRequest ticketRequest, UUID busId, UUID userId, UUID ticketId);
    CompletableFuture<Void> deleteById(UUID ticketId);
}
