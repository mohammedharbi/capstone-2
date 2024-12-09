package com.example.autowash.Service;

import com.example.autowash.ApiResponse.ApiException;
import com.example.autowash.Model.AutoWashCar;
import com.example.autowash.Model.OrderAutoWashCar;
import com.example.autowash.Model.User;
import com.example.autowash.Repository.AutoWashCarRepository;
import com.example.autowash.Repository.OrderAutoWashCarRepository;
import com.example.autowash.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AutoWashCarService {

    private final AutoWashCarRepository autoWashCarRepository;
    private final WashingMachineCarService washingMachineCarService;
    private final UserRepository userRepository;

    public List<AutoWashCar> getAllAutoWashCar() {
        return autoWashCarRepository.findAll();
    }

    public void addAutoWashAutoWashCar(AutoWashCar autoWashCar) {
        autoWashCarRepository.save(autoWashCar);
        washingMachineCarService.addWashingMachineByOrderCapacity(autoWashCar.getOrderCapacity(), autoWashCar.getId());
    }

    public void updateAutoWashCar(Integer id, AutoWashCar autoWashCar) {
        AutoWashCar oldAutoWashCar = autoWashCarRepository.findAutoWashCarById(id);

        if (oldAutoWashCar != null) {
            oldAutoWashCar.setStatus(autoWashCar.getStatus());
            oldAutoWashCar.setName(oldAutoWashCar.getName());
            oldAutoWashCar.setLocation(oldAutoWashCar.getLocation());
            autoWashCarRepository.save(oldAutoWashCar);
        } else throw new ApiException("Auto Wash Car not found");

    }

    public void deleteAutoWashCar(Integer id) {
        AutoWashCar OldAutoWashCar = autoWashCarRepository.findAutoWashCarById(id);

        if (OldAutoWashCar != null) {
            autoWashCarRepository.delete(OldAutoWashCar);
        } else throw new ApiException("cannot delete AutoWashCar, AutoWashCar not found");
    }

    //ex5
    public List<AutoWashCar> nearAutoWashCar(Integer userId, String keyword) {
        User checkUser = userRepository.findUserById(userId);
        if (checkUser == null) {
            throw new ApiException("user not found");
        }
        return autoWashCarRepository.getAutoWashCarsByLocationContainingIgnoreCase(keyword);
    }

    //ex6
    public List<AutoWashCar> openAutoWashCar(Integer userId) {
        User checkUser = userRepository.findUserById(userId);
        if (checkUser == null) {throw new ApiException("user not found");}
        return autoWashCarRepository.getAutoWashCarsByStatus();
    }

    //ex12
    public void changeOpenOrClosed(Integer autoWashCarId) {
        AutoWashCar checkAutoWashCar = autoWashCarRepository.findAutoWashCarById(autoWashCarId);
        if (checkAutoWashCar != null) {
            if (checkAutoWashCar.getStatus().equals("Open")) {
                checkAutoWashCar.setStatus("Closed");
                autoWashCarRepository.save(checkAutoWashCar);
            }else if (checkAutoWashCar.getStatus().equals("Closed")) {
                checkAutoWashCar.setStatus("Open");
            }
        }
    }



}