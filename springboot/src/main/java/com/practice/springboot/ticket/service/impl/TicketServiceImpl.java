package com.practice.springboot.ticket.service.impl;

import com.practice.springboot.bus.repostiory.BusRepository;
import com.practice.springboot.bus.service.BusService;
import com.practice.springboot.exceptions.MisMatchException;
import com.practice.springboot.exceptions.OccupiedException;
import com.practice.springboot.exceptions.OutOfRangeException;
import com.practice.springboot.exceptions.SeatsNotAvailable;
import com.practice.springboot.request.TicketRequest;
import com.practice.springboot.ticket.entity.TicketEntity;
import com.practice.springboot.ticket.repository.TicketRepository;
import com.practice.springboot.ticket.service.TicketService;
import com.practice.springboot.ticketStatus.TicketStatus;
import com.practice.springboot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    private final UserService userService;

    private final BusService busService;

    private final BusRepository busRepository;

    private CompletableFuture<Boolean> seatsAvailableOrNot(Set<Integer> bookedSeats, List<Integer> tickets,int totalSeats,int availableSeats){
        return CompletableFuture.supplyAsync(()->{
            if(availableSeats==0){
                throw new SeatsNotAvailable( "Seats Not Available");
            }
            List<Integer> alreadyBooked = new ArrayList<>();
            for(int i:tickets){
                if(i>totalSeats || i<0){
                    throw new OutOfRangeException("Seat Number Not with in the Range");
                }
                if(bookedSeats.contains(i)){
                    alreadyBooked.add(i);
                }
            }
            if(alreadyBooked.size()>0){
                throw new OccupiedException("Seats already booked "+alreadyBooked);
            }
            return true;
        });
    }

    public CompletableFuture<TicketEntity> persist(TicketRequest ticketRequest, UUID busId, UUID userId){
        return userService.getById(userId).thenCombine(busService.getById(busId),(userEntity, busEntity) -> {
            seatsAvailableOrNot(busEntity.getSeatsOccupied(),ticketRequest.getTicketNumbers(),busEntity.getTotalSeats(),busEntity.getAvailableSeats());
            Set<Integer> ticketOccupied = new HashSet<>();
            for(int i:ticketRequest.getTicketNumbers()){
                ticketOccupied.add(i);
            }
            busEntity.setAvailableSeats(busEntity.getAvailableSeats()-ticketRequest.getTicketNumbers().size());
            busEntity.setSeatsOccupied(ticketOccupied);
            busRepository.save(busEntity);
            TicketEntity ticketEntity = (TicketEntity.toEntity(ticketRequest,userId,busId));
            ticketEntity.setAmount(ticketRequest.getTicketNumbers().size()*busEntity.getTicketPrice());
            return ticketRepository.save(ticketEntity);
        });
    }

    public CompletableFuture<TicketEntity> getById(UUID ticketId){
        return CompletableFuture.supplyAsync(()->ticketRepository.fetchById(ticketId));
    }

    private CompletableFuture<Boolean> busIdAndUserIdMatchedWithCurrentUpdate(UUID ticketId,UUID busId,UUID userId){
        return getById(ticketId).thenApply(ticketEntity -> {
            if(ticketEntity.getUserId().equals(userId)){
                if(ticketEntity.getBusId().equals(busId)){
                    return true;
                }
                throw new MisMatchException("Bus Id is mismatched");
            }
            throw new MisMatchException("User Id is mismatched");
        });
    }
    public CompletableFuture<TicketEntity> updateTheTicketDetails(TicketRequest ticketRequest, UUID busId, UUID userId, UUID ticketId){
        return userService.getById(userId).thenCombine(busService.getById(busId),(userEntity, busEntity) -> {
                   busIdAndUserIdMatchedWithCurrentUpdate(ticketId,busId,userId);
                    Set<Integer> seatsOccupied = busEntity.getSeatsOccupied();
                    if (ticketRequest.getStatus() == TicketStatus.CANCEL) {
                        for (int i : ticketRequest.getTicketNumbers()) {
                            if(!seatsOccupied.contains(i)) {
                                continue;
                            }
                            seatsOccupied.remove(i);
                        }
                        busEntity.setAvailableSeats(busEntity.getAvailableSeats() + ticketRequest.getTicketNumbers().size());
                        busEntity.setSeatsOccupied(seatsOccupied);
                    } else {
                        seatsAvailableOrNot(busEntity.getSeatsOccupied(), ticketRequest.getTicketNumbers(), busEntity.getTotalSeats(), busEntity.getAvailableSeats());
                        for (int i : ticketRequest.getTicketNumbers()) {
                            seatsOccupied.add(i);
                        }
                        busEntity.setSeatsOccupied(seatsOccupied);
                        busEntity.setAvailableSeats(busEntity.getAvailableSeats() + ticketRequest.getTicketNumbers().size());
                    }
                    return busEntity;
        }).thenApply(busEntity -> {
            TicketEntity ticketEntity = ticketRepository.fetchById(ticketId);
            List<Integer> ticketsBooking = ticketEntity.getTicketNumbers();
            if(ticketRequest.getStatus()==TicketStatus.CANCEL){
                for(int i:ticketRequest.getTicketNumbers()){
                    if(!ticketsBooking.contains(i)){
                        continue;
                    }
                    ticketsBooking.remove(Integer.valueOf(i));
                }
                ticketEntity.setTicketNumbers(ticketsBooking);
                if(ticketsBooking.size()==0){
                    ticketEntity.setStatus(TicketStatus.CANCEL);
                }
            }
            else{
                for(int i:ticketRequest.getTicketNumbers()){
                    if(ticketsBooking.contains(i)){
                        continue;
                    }
                    ticketsBooking.add(i);
                }
            }
            busRepository.save(busEntity);
            ticketEntity.setAmount(ticketEntity.getTicketNumbers().size()*busEntity.getTicketPrice());
            return ticketRepository.save(ticketEntity);
        });
    }

    public CompletableFuture<Void> deleteById(UUID ticketId){
        return getById(ticketId).thenAccept(ticketEntity -> {
            ticketRepository.deleteById(ticketId);
        });
    }

}
