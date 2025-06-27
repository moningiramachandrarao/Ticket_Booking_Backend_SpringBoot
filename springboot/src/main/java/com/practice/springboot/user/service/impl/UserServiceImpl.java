package com.practice.springboot.user.service.impl;

import com.practice.springboot.card.entity.CardEntity;
import com.practice.springboot.card.repository.CardRepository;
import com.practice.springboot.exceptions.MisMatchException;
import com.practice.springboot.request.CardRequest;
import com.practice.springboot.request.UserRequest;
import com.practice.springboot.user.entity.UserEntity;
import com.practice.springboot.user.repository.UserRepository;
import com.practice.springboot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final CardRepository cardRepository;

    public CompletableFuture<UserEntity> persist(UserRequest userRequest){
        return CompletableFuture.supplyAsync(()-> {
            CardEntity cardEntity = cardRepository.save(CardEntity.toEntity(userRequest.getCardRequest()));
            UserEntity userEntity = UserRequest.toEntity(userRequest);
            userEntity.setCardEntity(cardEntity);
            return  userRepository.save(userEntity);
        });
    }

    public CompletableFuture<UserEntity> updateUserDetails(UserRequest userRequest, UUID userId,UUID cardId){
      UserEntity userEntity = userRepository.fetchById(userId);
      if(userRequest.getEmail()!=null){
          userEntity.setEmail(userRequest.getEmail());
      }
      if(userRequest.getPassword()!=null){
          userEntity.setPassword(userRequest.getPassword());
      }
      if(userRequest.getCardRequest()!=null){
          CardRequest cardRequest = userRequest.getCardRequest();
          CardEntity cardEntity = cardRepository.fetchById(cardId);
          if(cardRequest.getCardNo()!=null){
              cardEntity.setCardNo(cardRequest.getCardNo());
          }
          if(cardRequest.getCvv()!=null){
              cardEntity.setCvv(cardRequest.getCvv());
          }
          if(cardRequest.getBalance()!=0.0){
              cardEntity.setBalance(cardRequest.getBalance());
          }

          userEntity.setCardEntity(cardRepository.save(cardEntity));
      }

      return CompletableFuture.supplyAsync(()-> userRepository.save(userEntity));
    }

    public CompletableFuture<UserEntity> getById(UUID userId){
        return CompletableFuture.supplyAsync(()-> userRepository.fetchById(userId));
    }

    public CompletableFuture<UserEntity> getByEmail(String email){
        return CompletableFuture.supplyAsync(()->userRepository.getByEmail(email));
    }

    public CompletableFuture<UserEntity> login(String email,String password){
        return getByEmail(email).thenApply(userEntity -> {
            if(userEntity.getPassword().equals(password)){
                return userEntity;
            }
            throw new MisMatchException("Password mismatch");
        });
    }

}
