package com.practice.springboot.bus.service;

import com.practice.springboot.request.BusRequest;
import com.practice.springboot.bus.entity.BusEntity;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface BusService {
    CompletableFuture<BusEntity> persist(BusRequest busRequest, UUID userId);
    CompletableFuture<BusEntity> getById(UUID busId);
    CompletableFuture<BusEntity> updateTheDetails(BusRequest busRequest,UUID userId,UUID busId);

}
