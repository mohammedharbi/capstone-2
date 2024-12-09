package com.example.autowash.Controller;

import com.example.autowash.Model.ClothesServiceMenu;
import com.example.autowash.Service.ClothesServiceMenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/clothes-service-menu")
@RequiredArgsConstructor
public class ClothesServiceMenuController {

    private final ClothesServiceMenuService clothesServiceMenuService;

    @GetMapping("/get")
    public ResponseEntity getClothesServiceMenus(){
        return ResponseEntity.status(200).body(clothesServiceMenuService.getAllClothesServiceMenu());
    }

    @PostMapping("/add")
    public ResponseEntity addClothesServiceMenu(@RequestBody @Valid ClothesServiceMenu clothesService, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        clothesServiceMenuService.addClothesServiceMenu(clothesService);
        return ResponseEntity.status(201).body("Clothes service added");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateClothesServiceMenu(@PathVariable Integer id, @RequestBody @Valid ClothesServiceMenu clothesServiceMenu, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        clothesServiceMenuService.updateClothesServiceMenu(id, clothesServiceMenu);
        return ResponseEntity.status(201).body("clothes service updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteClothesServiceMenu(@PathVariable Integer id){
        clothesServiceMenuService.deleteClothesServiceMenu(id);
        return ResponseEntity.status(201).body("clothes service deleted");
    }

    @PutMapping("/add-discount/auto-wash-clothes/service-id/{serviceId}/discount/{discount}")
    public ResponseEntity addDiscount(@PathVariable Integer serviceId, @PathVariable Integer discount){
        clothesServiceMenuService.addDiscount(serviceId, discount);
        return ResponseEntity.status(201).body("Discount added");
    }

    @GetMapping("/get-service-byauto-wash-clothes/{autoWashClothesId}")
    public ResponseEntity getServiceByAutoWashClothes(@PathVariable Integer autoWashClothesId){
        return ResponseEntity.status(200).body(clothesServiceMenuService.getServiceByAutoWashClothes(autoWashClothesId));
    }

    @GetMapping("/get-cheapest-price/{userId}")
    public ResponseEntity getCheapestPrice(@PathVariable Integer userId){
        return ResponseEntity.status(200).body(clothesServiceMenuService.getCheapestPrice(userId));
    }
}
