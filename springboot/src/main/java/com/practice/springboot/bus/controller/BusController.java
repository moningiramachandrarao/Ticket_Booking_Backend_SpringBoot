package com.practice.springboot.bus.controller;

import com.practice.springboot.request.BusRequest;
import com.practice.springboot.bus.service.BusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/bus")
@RequiredArgsConstructor
public class BusController {

    private final BusService busService;

    @PostMapping("/register/user/{user_id}")
    public CompletableFuture<ResponseEntity<Map<String,Object>>> create(@RequestBody BusRequest busRequest, @PathVariable UUID user_id){
        return busService.persist(busRequest,user_id).thenApply(busEntity -> {
            Map<String,Object> response = new HashMap<>();
            response.put("Status", HttpStatus.CREATED);
            response.put("Message","New Bus Created Successfully");
            response.put("Details",busEntity);
            return new ResponseEntity<>(response,HttpStatus.CREATED);
        });
    }

    @GetMapping("/{bus_id}")
    public CompletableFuture<ResponseEntity<Map<String,Object>>> getById(@PathVariable UUID bus_id) {
        return busService.getById(bus_id).thenApply(busEntity -> {
            Map<String, Object> response = new HashMap<>();
            response.put("Status", HttpStatus.OK);
            response.put("Message", "Bus details retrived Successfully");
            response.put("Details", busEntity);
            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }
    @PutMapping("/{bus_id}/user/{user_id}")
    public CompletableFuture<ResponseEntity<Map<String,Object>>> updateBusDetails(@RequestBody BusRequest busRequest,@PathVariable UUID user_id,@PathVariable UUID bus_id){
        return busService.updateTheDetails(busRequest,user_id,bus_id).thenApply(busEntity -> {
            Map<String, Object> response = new HashMap<>();
            response.put("Status", HttpStatus.OK);
            response.put("Message", "Bus details updated Successfully");
            response.put("Details", busEntity);
            return new ResponseEntity<>(response, HttpStatus.OK);
        });
    }



}
