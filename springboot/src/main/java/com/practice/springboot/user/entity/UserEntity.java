package com.practice.springboot.user.entity;


import com.practice.springboot.card.entity.CardEntity;
import com.practice.springboot.role.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.UUID;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserEntity {

    @Id
    @GeneratedValue
    private UUID userId;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column( columnDefinition = "jsonb")
    private CardEntity cardEntity;
}
