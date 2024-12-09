package com.example.autowash.Repository;

import com.example.autowash.Model.WashingMachineCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WashingMachineCarRepository extends JpaRepository<WashingMachineCar, Integer> {

    WashingMachineCar findWashingMachineCarById(Integer id);
}
