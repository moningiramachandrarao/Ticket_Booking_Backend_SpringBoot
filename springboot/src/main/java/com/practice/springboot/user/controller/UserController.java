package com.practice.springboot.user.controller;

import com.practice.springboot.request.UserRequest;
import com.practice.springboot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> userRegistration(@RequestBody  UserRequest userRequest){
        return userService.persist(userRequest).thenApply(userEntity -> {
            Map<String,Object> response = new HashMap<>();
            response.put("Status", HttpStatus.CREATED);
            response.put("Message","User added Successfully");
            response.put("User Details",userEntity);
            return new ResponseEntity(response,HttpStatus.CREATED);
        });
    }

    @PostMapping("/login/email/{email}/password/{password}")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> login(@PathVariable String email,@PathVariable String password){
        return userService.login(email,password).thenApply((userEntity)->{
            Map<String,Object> response = new HashMap<>();
            response.put("Status", HttpStatus.OK);
            response.put("Message","User login Successfully");
            response.put("User Details",userEntity);
            return new ResponseEntity(response,HttpStatus.OK);
        });
    }

    @PutMapping("/{user_id}/card/{card_id}")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> updateDetails(@RequestBody UserRequest userRequest, @PathVariable UUID user_id,@PathVariable UUID card_id){
        return userService.updateUserDetails(userRequest,user_id,card_id).thenApply((userEntity )->{
            Map<String,Object> response = new HashMap<>();
            response.put("Status", HttpStatus.OK);
            response.put("Message","User updated Successfully");
            response.put("User Details",userEntity);
            return new ResponseEntity(response,HttpStatus.OK);
        });
    }

    @GetMapping("/{user_id}")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> getById(@PathVariable UUID user_id){
        return userService.getById(user_id).thenApply(userEntity -> {
            Map<String,Object> response = new HashMap<>();
            response.put("Status", HttpStatus.OK);
            response.put("Message","User retrived Successfully");
            response.put("User Details",userEntity);
            return new ResponseEntity(response,HttpStatus.OK);
        });
    }

    @GetMapping("/email/{email}")
    public CompletableFuture<ResponseEntity<Map<String, Object>>> getByEmail(@PathVariable String email){
        return userService.getByEmail(email).thenApply(userEntity -> {
            Map<String,Object> response = new HashMap<>();
            response.put("Status", HttpStatus.OK);
            response.put("Message","User retrived Successfully");
            response.put("User Details",userEntity);
            return new ResponseEntity(response,HttpStatus.OK);
        });
    }
}
