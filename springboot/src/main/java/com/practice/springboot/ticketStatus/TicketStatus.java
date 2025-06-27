package com.practice.springboot.ticketStatus;


import com.practice.springboot.role.Role;

public enum TicketStatus {
    PENDING("pending"),
    PAID("PAID"),
    CANCEL("CANCEL");

    private final String value;

    TicketStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TicketStatus fromValue(String value) {
        for (TicketStatus status : values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown role: " + value);
    }
}
