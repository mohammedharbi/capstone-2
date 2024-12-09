package com.example.autowash.Service;

import com.example.autowash.ApiResponse.ApiException;
import com.example.autowash.Model.*;
import com.example.autowash.Repository.AutoWashClothesRepository;
import com.example.autowash.Repository.WashingMachineClothesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class WashingMachineClothesService {

    private final WashingMachineClothesRepository washingMachineClothesRepository;
    private final AutoWashClothesRepository autoWashClothesRepository;

    public List<WashingMachineClothes> getAllWashingMachineClothes(){
        return washingMachineClothesRepository.findAll();
    }

    public void addWashingMachineClothes(WashingMachineClothes washingMachineClothes){
        AutoWashClothes checkAutoWashClothes = autoWashClothesRepository.findAutoWashClothesById(washingMachineClothes.getAutoWashClothesId());

        if (checkAutoWashClothes != null) {
            washingMachineClothes.setCreatedAt(LocalDateTime.now());
            washingMachineClothesRepository.save(washingMachineClothes);
            checkAutoWashClothes.setOrderCapacity(checkAutoWashClothes.getOrderCapacity() + 1);
            autoWashClothesRepository.save(checkAutoWashClothes);

        }else throw new ApiException("AutoWashClothes not found");

    }

    public void updateWashingMachineClothes(Integer id, WashingMachineClothes washingMachineClothes){
        WashingMachineClothes oldWashingMachineClothes = washingMachineClothesRepository.findWashingMachineClothesById(id);
        if (oldWashingMachineClothes != null) {
            oldWashingMachineClothes.setName(washingMachineClothes.getName());
            washingMachineClothesRepository.save(oldWashingMachineClothes);
        }else throw new ApiException("washing machine not found");
    }

    public void deleteWashingMachineClothes(Integer id){
        WashingMachineClothes oldWashingMachineClothes = washingMachineClothesRepository.findWashingMachineClothesById(id);
        if (oldWashingMachineClothes == null) {throw new ApiException("washing machine clothes not found");}

        AutoWashClothes checkAutoWashClothes = autoWashClothesRepository.findAutoWashClothesById(oldWashingMachineClothes.getAutoWashClothesId());

        washingMachineClothesRepository.delete(oldWashingMachineClothes);

        if (checkAutoWashClothes.getOrderCapacity() >= 1){
            checkAutoWashClothes.setOrderCapacity(checkAutoWashClothes.getOrderCapacity() - 1);
            autoWashClothesRepository.save(checkAutoWashClothes);}
    }


    public void addWashingMachineByOrderCapacity(Integer orderCapacity,Integer autoWashClothesId){
        for (int i = 1; i <= orderCapacity; i++) {
            WashingMachineClothes newWashingMachineClothes = new WashingMachineClothes();
            newWashingMachineClothes.setWashingMachineStatus("Available");
            newWashingMachineClothes.setName("Washing Machine "+i);
            newWashingMachineClothes.setAutoWashClothesId(autoWashClothesId);
            newWashingMachineClothes.setCreatedAt(LocalDateTime.now());
            washingMachineClothesRepository.save(newWashingMachineClothes);
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
    public void validatePin(Integer washingMachineClothesId, String pin) {
        WashingMachineClothes checkWashingMachineClothes = washingMachineClothesRepository.findWashingMachineClothesById(washingMachineClothesId);
        if (checkWashingMachineClothes == null) {throw new ApiException("washing machine clothes not found");}

        if (!checkWashingMachineClothes.getPin().equals(pin)) {throw new ApiException("pin not match");}
    }



}
