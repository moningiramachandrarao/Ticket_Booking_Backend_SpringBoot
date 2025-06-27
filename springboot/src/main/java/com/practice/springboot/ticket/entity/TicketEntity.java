package com.practice.springboot.ticket.entity;

import com.practice.springboot.request.TicketRequest;
import com.practice.springboot.ticketStatus.TicketStatus;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "ticket")
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TicketEntity {

    @Id
    @GeneratedValue
    private UUID ticketId;

    private UUID userId;

    private UUID busId;

    private List<Integer> ticketNumbers;

    private LocalDate bookingDate;

    private LocalTime bookingTime;

    private Double amount;

    private TicketStatus status;

    @PrePersist
    protected void onCreate() {
        this.bookingDate = LocalDate.now();
        this.bookingTime = LocalTime.now();
        if (this.status == null) {
            this.status = TicketStatus.PENDING;
        }
    }


    public static TicketEntity toEntity(TicketRequest ticketRequest,UUID userId,UUID busId){
        return TicketEntity.builder()
                .ticketNumbers(ticketRequest.getTicketNumbers())
                .busId(busId)
                .userId(userId)
                .status(ticketRequest.getStatus())
                .build();
    }

}
