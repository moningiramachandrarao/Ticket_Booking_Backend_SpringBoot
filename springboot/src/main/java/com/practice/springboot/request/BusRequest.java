package com.practice.springboot.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
public class BusRequest {
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
}
