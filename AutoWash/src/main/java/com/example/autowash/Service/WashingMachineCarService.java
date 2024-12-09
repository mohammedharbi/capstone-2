package com.example.autowash.Service;

import com.example.autowash.ApiResponse.ApiException;
import com.example.autowash.Model.AutoWashCar;
import com.example.autowash.Model.WashingMachineCar;
import com.example.autowash.Repository.AutoWashCarRepository;
import com.example.autowash.Repository.WashingMachineCarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WashingMachineCarService {

    private final WashingMachineCarRepository washingMachineCarRepository;
    private final AutoWashCarRepository autoWashCarRepository;

    public List<WashingMachineCar> getWashingMachinesCar() {
        return washingMachineCarRepository.findAll();
    }

    public void addWashingMachineCar(WashingMachineCar washingMachineCar) {
        AutoWashCar checkAutoWashCar = autoWashCarRepository.findAutoWashCarById(washingMachineCar.getAutoWashCarId());

        if (checkAutoWashCar != null) {
            washingMachineCar.setCreatedAt(LocalDateTime.now());
            washingMachineCarRepository.save(washingMachineCar);
            checkAutoWashCar.setOrderCapacity(checkAutoWashCar.getOrderCapacity() + 1);
            autoWashCarRepository.save(checkAutoWashCar);

        }else throw new ApiException("AutoWashCar not found");

    }

    public void updateWashingMachineCar(Integer id,WashingMachineCar washingMachineCar) {
        WashingMachineCar oldWashingMachineCar = washingMachineCarRepository.findWashingMachineCarById(id);

        if (oldWashingMachineCar != null) {
            oldWashingMachineCar.setName(washingMachineCar.getName());
            washingMachineCarRepository.save(oldWashingMachineCar);
        }else throw new ApiException("washing machine not found");
    }

    public void deleteWashingMachineCar(Integer id) {
        WashingMachineCar oldWashingMachineCar = washingMachineCarRepository.findWashingMachineCarById(id);
        if (oldWashingMachineCar == null) {throw new ApiException("washing machine car not found");}

        AutoWashCar checkAutoWashCar = autoWashCarRepository.findAutoWashCarById(oldWashingMachineCar.getAutoWashCarId());

            washingMachineCarRepository.delete(oldWashingMachineCar);

            if (checkAutoWashCar.getOrderCapacity() >= 1){
            checkAutoWashCar.setOrderCapacity(checkAutoWashCar.getOrderCapacity() - 1);
            autoWashCarRepository.save(checkAutoWashCar);}
    }

    public void addWashingMachineByOrderCapacity(Integer orderCapacity,Integer autoWashCarId) {
        for (int i = 1; i <= orderCapacity; i++) {
            WashingMachineCar newWashingMachineCar = new WashingMachineCar();
            newWashingMachineCar.setAutoWashCarId(autoWashCarId);
            newWashingMachineCar.setWashingMachineStatus("Available");
            newWashingMachineCar.setName("Washing Machine "+i);
            newWashingMachineCar.setCreatedAt(LocalDateTime.now());
            washingMachineCarRepository.save(newWashingMachineCar);
        }
    }

    //ex 6
    public String generatePin() {

        String randoms = "0123456789";
        Random random = new Random();
        StringBuilder randomToCombine = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(randoms.length());
            randomToCombine.append(randoms.charAt(index));
        }
        return randomToCombine.toString();
    }

    //ex13
    public void validatePin(Integer washingMachineCarId, String pin) {
        WashingMachineCar checkWashingMachineCar = washingMachineCarRepository.findWashingMachineCarById(washingMachineCarId);
        if (checkWashingMachineCar == null) {throw new ApiException("washing machine car not found");}

        if (!checkWashingMachineCar.getPin().equals(pin)) {throw new ApiException("pin not match");}
    }



}