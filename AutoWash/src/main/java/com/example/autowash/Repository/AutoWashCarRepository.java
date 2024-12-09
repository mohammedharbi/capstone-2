package com.example.autowash.Repository;

import com.example.autowash.Model.AutoWashCar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoWashCarRepository extends JpaRepository<AutoWashCar, Integer> {

    AutoWashCar findAutoWashCarById(Integer id);
    List<AutoWashCar> getAutoWashCarsByLocationContainingIgnoreCase(String brand);

    @Query("select a from AutoWashCar a where a.status = 'Open'")
    List<AutoWashCar> getAutoWashCarsByStatus();


}
