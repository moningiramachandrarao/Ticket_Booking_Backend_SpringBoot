package com.practice.springboot.bus.service.impl;
import com.practice.springboot.bus.service.BusService;
import com.practice.springboot.exceptions.AdminException;
import com.practice.springboot.exceptions.MisMatchException;
import com.practice.springboot.request.BusRequest;
import com.practice.springboot.bus.entity.BusEntity;
import com.practice.springboot.bus.repostiory.BusRepository;
import com.practice.springboot.role.Role;
import com.practice.springboot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {

    private final BusRepository busRepository;

    private final UserService userService;

    public CompletableFuture<Boolean> isAdmin(UUID userId) {
        return userService.getById(userId).thenApply(userEntity -> {
            if(Role.ADMIN==userEntity.getRole()){
                return true;
            }
            return false;
        });
    }

    public CompletableFuture<BusEntity> persist(BusRequest busRequest, UUID userId){
        return isAdmin(userId).thenApply((flag)->{
            if(flag){
                if(busRequest.getAvailableSeats()==busRequest.getTotalSeats()){
                    return busRepository.save(BusEntity.toEntity(busRequest,userId));
                }
                throw new RuntimeException("Total Seats and Available seats while creating bus");
            }
            throw new AdminException("Admin can create the bus");
        });
    }

    public CompletableFuture<BusEntity> getById(UUID busId){
        return CompletableFuture.supplyAsync(()-> busRepository.fetchById(busId));
    }

    public CompletableFuture<BusEntity> updateTheDetails(BusRequest busRequest,UUID userId,UUID busId){
        return isAdmin(userId).thenCombine(getById(busId),(flag,busEntity)->{
            if(flag){
                if(busEntity.getBusId().equals(busId)){
                    if(!userId.equals(busEntity.getUserId())){
                        throw new MisMatchException("Admin mismatched");
                    }
                    if(busRequest.getBusNo()!=null){
                        busEntity.setBusNo(busRequest.getBusNo());
                    }
                    if(busRequest.getTotalSeats()!=0){
                        busEntity.setAvailableSeats(busRequest.getTotalSeats());
                    }
                    if(busRequest.getAvailableSeats()!=0){
                        busEntity.setAvailableSeats(busRequest.getAvailableSeats());
                    }
                    if(busRequest.getBrand()!=null){
                        busEntity.setBrand(busRequest.getBrand());
                    }
                    if(busRequest.getModel()!=null){
                        busEntity.setModel(busRequest.getModel());
                    }
                    if(busRequest.getDestination()!=null){
                        busEntity.setDestination(busRequest.getDestination());
                    }
                    if(busRequest.getSource()!=null){
                        busEntity.setSource(busRequest.getSource());
                    }
                    if(busRequest.getStartDate()!=null){
                        busEntity.setStartDate(busRequest.getStartDate());
                    }
                    if(busRequest.getStartTime()!=null){
                        busEntity.setStartTime(busRequest.getStartTime());
                    }
                    if(busRequest.getTicketPrice()!=0.0){
                        busEntity.setTicketPrice(busRequest.getTicketPrice());
                    }
                    return busRepository.save(busEntity);
                }
                throw new MisMatchException("Bus Id not matched");
            }
            throw new AdminException("Admin not changing details");
        });
    }



}
