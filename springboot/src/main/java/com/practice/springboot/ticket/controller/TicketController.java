package com.practice.springboot.ticket.controller;

import com.practice.springboot.request.TicketRequest;
import com.practice.springboot.ticket.service.impl.TicketServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RequestMapping("/ticket")
@RestController
@RequiredArgsConstructor
public class TicketController {

    private final TicketServiceImpl ticketService;

    @PostMapping("/user/{user_id}/bus/{bus_id}")
    public CompletableFuture<ResponseEntity<Map<String,Object>>> ticketBooking(@RequestBody TicketRequest ticketRequest, @PathVariable UUID user_id,@PathVariable UUID bus_id){
        return ticketService.persist(ticketRequest,bus_id,user_id).thenApply((ticketEntity)->{
            Map<String,Object> response = new HashMap<>();
            response.put("Message","Tickets Booked successfully");
            response.put("Status", HttpStatus.CREATED);
            response.put("Details",ticketEntity);
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        });
    }

    @PutMapping("/{ticket_id}/user/{user_id}/bus/{bus_id}")
    public CompletableFuture<ResponseEntity<Map<String,Object>>> updateDetailsOfTicket(@RequestBody TicketRequest ticketRequest,@PathVariable UUID ticket_id,@PathVariable UUID user_id,@PathVariable UUID bus_id){
        return ticketService.updateTheTicketDetails(ticketRequest,bus_id,user_id,ticket_id).thenApply(ticketEntity -> {
            Map<String,Object> response = new HashMap<>();
            response.put("Message","Ticket Details Updated successfully");
            response.put("Status", HttpStatus.OK);
            response.put("Details",ticketEntity);
            return new ResponseEntity<>(response,HttpStatus.OK);
        });
    }

    @GetMapping("/{ticket_id}")
    public CompletableFuture<ResponseEntity<Map<String,Object>>> getById(@PathVariable UUID ticket_id) {
        return ticketService.getById(ticket_id).thenApply(ticketEntity -> {
            Map<String, Object> response = new HashMap<>();
            response.put("Message", "Ticket Details retrived successfully");
            response.put("Status", HttpStatus.OK);
            response.put("Details", ticketEntity);
            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }
}
