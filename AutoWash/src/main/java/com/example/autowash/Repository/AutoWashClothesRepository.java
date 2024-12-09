package com.example.autowash.Repository;

import com.example.autowash.Model.AutoWashCar;
import com.example.autowash.Model.AutoWashClothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutoWashClothesRepository extends JpaRepository<AutoWashClothes,Integer> {

    AutoWashClothes findAutoWashClothesById(Integer id);

    List<AutoWashClothes> getAutoWashClothesByLocationContainingIgnoreCase(String brand);

    @Query("select a from AutoWashCar a where a.status = 'Open'")
    List<AutoWashClothes> getAutoWashClothesByStatus();
}