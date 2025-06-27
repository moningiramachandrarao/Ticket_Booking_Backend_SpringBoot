package com.practice.springboot.bus.repostiory;

import com.practice.springboot.bus.entity.BusEntity;
import com.practice.springboot.exceptions.MisMatchException;
import com.practice.springboot.exceptions.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BusRepository  extends JpaRepository<BusEntity, UUID> {
    default BusEntity fetchById(UUID busId){
        return findById(busId).orElseThrow(()->{
            throw new NotFoundException("Bus not present");
        });
    }
}
