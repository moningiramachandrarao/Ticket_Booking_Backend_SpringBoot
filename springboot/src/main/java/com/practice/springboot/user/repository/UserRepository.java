package com.practice.springboot.user.repository;

import com.practice.springboot.exceptions.MisMatchException;
import com.practice.springboot.exceptions.NotFoundException;
import com.practice.springboot.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    default UserEntity fetchById(UUID userId){
        return findById(userId).orElseThrow(()->new NotFoundException("User is not present"));
    }
    UserEntity getByEmail(String email);
}
