package com.example.autowash.Repository;

import com.example.autowash.Model.WashingMachineClothes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WashingMachineClothesRepository extends JpaRepository<WashingMachineClothes,Integer> {

    WashingMachineClothes findWashingMachineClothesById(Integer id);
}
