package com.practice.springboot.bus.entity;

import com.practice.springboot.request.BusRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
@Table(name="car")
@NoArgsConstructor
@Entity
public class BusEntity {

    @Id
    @GeneratedValue
    private UUID busId;

    private String busNo;

    private String brand;

    private String model;

    private String source;

    private String destination;

    private LocalDate startDate;

    private LocalTime startTime;

    private int totalSeats;

    private int availableSeats;

    private double ticketPrice;

    private UUID userId;

    private Set<Integer> seatsOccupied;

    public static BusEntity toEntity(BusRequest busRequest,UUID userId){
        return BusEntity.builder()
                .busNo(busRequest.getBusNo())
                .availableSeats(busRequest.getAvailableSeats())
                .brand(busRequest.getBrand())
                .destination(busRequest.getDestination())
                .source(busRequest.getSource())
                .startDate(busRequest.getStartDate())
                .ticketPrice(busRequest.getTicketPrice())
                .model(busRequest.getModel())
                .startTime(busRequest.getStartTime())
                .userId(userId)
                .totalSeats(busRequest.getTotalSeats())
                .seatsOccupied(new HashSet<>())
                .build();
    }
}
