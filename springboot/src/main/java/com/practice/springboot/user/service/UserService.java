package com.practice.springboot.user.service;

import com.practice.springboot.request.UserRequest;
import com.practice.springboot.user.entity.UserEntity;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    CompletableFuture<UserEntity> persist(UserRequest userRequest);
    CompletableFuture<UserEntity> updateUserDetails(UserRequest userRequest, UUID userId,UUID cardId);
    CompletableFuture<UserEntity> getById(UUID userId);
    CompletableFuture<UserEntity> getByEmail(String email);
    CompletableFuture<UserEntity> login(String email,String password);

}
