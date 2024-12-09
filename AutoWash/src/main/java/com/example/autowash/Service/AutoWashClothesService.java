package com.example.autowash.Service;

import com.example.autowash.ApiResponse.ApiException;
import com.example.autowash.Model.AutoWashClothes;
import com.example.autowash.Model.User;
import com.example.autowash.Repository.AutoWashClothesRepository;
import com.example.autowash.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AutoWashClothesService {

    private final AutoWashClothesRepository autoWashClothesRepository;
    private final WashingMachineClothesService washingMachineClothesService;
    private final UserRepository userRepository;
    ;

    public List<AutoWashClothes> getAllAutoWashClothes() {
        return autoWashClothesRepository.findAll();
    }

    public void addAutoWashAutoWashClothes(AutoWashClothes autoWashClothes) {
        autoWashClothesRepository.save(autoWashClothes);
        washingMachineClothesService.addWashingMachineByOrderCapacity(autoWashClothes.getOrderCapacity(), autoWashClothes.getId());
    }

    public void updateAutoWashClothes(Integer id, AutoWashClothes autoWashClothes) {
        AutoWashClothes oldAutoWashClothes = autoWashClothesRepository.findAutoWashClothesById(id);

        if (oldAutoWashClothes != null) {
            oldAutoWashClothes.setStatus(autoWashClothes.getStatus());
            oldAutoWashClothes.setName(oldAutoWashClothes.getName());
            oldAutoWashClothes.setLocation(oldAutoWashClothes.getLocation());
            autoWashClothesRepository.save(oldAutoWashClothes);
        } else throw new ApiException("Auto Wash Clothes not found");

    }

    public void deleteAutoWashClothes(Integer id) {
        AutoWashClothes OldAutoWashClothes = autoWashClothesRepository.findAutoWashClothesById(id);

        if (OldAutoWashClothes != null) {
            autoWashClothesRepository.delete(OldAutoWashClothes);
        }else throw new ApiException("Auto Wash Clothes, Clothes not found");
    }

    //ex5
    public List<AutoWashClothes> nearAutoWashClothes(Integer userId, String keyword) {
        User checkUser = userRepository.findUserById(userId);
        if (checkUser == null) {
            throw new ApiException("user not found");
        }
        return autoWashClothesRepository.getAutoWashClothesByLocationContainingIgnoreCase(keyword);
    }

    //ex6
    public List<AutoWashClothes> openAutoWashClothes(Integer userId) {
        User checkUser = userRepository.findUserById(userId);
        if (checkUser == null) {throw new ApiException("user not found");}
        return autoWashClothesRepository.getAutoWashClothesByStatus();
    }

    //ex12
    public void changeOpenOrClosed(Integer autoWashCarId) {
        AutoWashClothes checkAutoWashClothes = autoWashClothesRepository.findAutoWashClothesById(autoWashCarId);
        if (checkAutoWashClothes != null) {
            if (checkAutoWashClothes.getStatus().equals("Open")) {
                checkAutoWashClothes.setStatus("Closed");
                autoWashClothesRepository.save(checkAutoWashClothes);
            }else if (checkAutoWashClothes.getStatus().equals("Closed")) {
                checkAutoWashClothes.setStatus("Open");
            }
        }
    }
}

