package com.practice.springboot.request;

import com.practice.springboot.card.entity.CardEntity;
import com.practice.springboot.role.Role;
import com.practice.springboot.user.entity.UserEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRequest {

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private CardRequest cardRequest;

    public static UserEntity toEntity(UserRequest userRequest){
        return UserEntity.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .role(userRequest.getRole())
                .build();
    }

}
