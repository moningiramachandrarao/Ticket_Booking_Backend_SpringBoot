package com.practice.springboot.request;

import com.practice.springboot.ticketStatus.TicketStatus;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class TicketRequest {

    private List<Integer> ticketNumbers;

    private TicketStatus status;
}
