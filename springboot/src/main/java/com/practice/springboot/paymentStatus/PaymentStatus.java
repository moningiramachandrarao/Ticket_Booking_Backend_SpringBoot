package com.practice.springboot.paymentStatus;

import com.practice.springboot.ticketStatus.TicketStatus;

public enum PaymentStatus {
    SUCCESS("success"),
    FAIL("fail");

    private final String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PaymentStatus fromValue(String value) {
        for (PaymentStatus status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + value);
    }
}
