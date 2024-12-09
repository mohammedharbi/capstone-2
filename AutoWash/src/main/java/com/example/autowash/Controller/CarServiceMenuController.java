package com.example.autowash.Controller;

import com.example.autowash.Model.CarServiceMenu;
import com.example.autowash.Service.CarServicesMenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/car-service-menu")
@RequiredArgsConstructor
public class CarServiceMenuController {

    private final CarServicesMenuService carServicesMenuService;

    @GetMapping("/get")
    public ResponseEntity getCarServiceMenus(){
        return ResponseEntity.status(200).body(carServicesMenuService.getAllCarServiceMenu());
    }

    @PostMapping("/add")
    public ResponseEntity addCarServiceMenu(@RequestBody @Valid CarServiceMenu carServiceMenu, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        carServicesMenuService.addCarServiceMenu(carServiceMenu);
        return ResponseEntity.status(201).body("Car service added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCarServiceMenu(@PathVariable Integer id, @RequestBody @Valid CarServiceMenu carServiceMenu, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        carServicesMenuService.updateCarServiceMenu(id, carServiceMenu);
        return ResponseEntity.status(201).body("Car service updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCarServiceMenu(@PathVariable Integer id){
        carServicesMenuService.deleteCarServiceMenu(id);
        return ResponseEntity.status(201).body("Car service deleted");
    }

    @PutMapping("/add-discount/auto-wash-car/service-id/{serviceId}/discount/{discount}")
    public ResponseEntity addDiscount(@PathVariable Integer serviceId, @PathVariable Integer discount){
        carServicesMenuService.addDiscount(serviceId, discount);
        return ResponseEntity.status(201).body("Discount added");
    }

    @GetMapping("/get-service-byauto-wash-car/{autoWashCarId}")
    public ResponseEntity getServiceByAutoWashCar(@PathVariable Integer autoWashCarId){
        return ResponseEntity.status(200).body(carServicesMenuService.getServiceByAutoWashCar(autoWashCarId));
    }

    @GetMapping("/get-cheapest-price/{userId}")
    public ResponseEntity getCheapestPrice(@PathVariable Integer userId){
        return ResponseEntity.status(200).body(carServicesMenuService.getCheapestPrice(userId));
    }

}
