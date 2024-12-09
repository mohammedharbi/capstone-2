package com.example.autowash.Controller;

import com.example.autowash.ApiResponse.ApiResponse;
import com.example.autowash.Model.WashingMachineClothes;
import com.example.autowash.Service.WashingMachineClothesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/washing-machine-clothes")
@RequiredArgsConstructor
public class WashingMachineClothesController {

    private final WashingMachineClothesService washingMachineClothesService;

    @GetMapping("/get")
    public ResponseEntity getWashingMachineClothes(){
        return ResponseEntity.status(200).body(washingMachineClothesService.getAllWashingMachineClothes());
    }

    @PostMapping("/add")
    public ResponseEntity addWashingMachineClothes(@RequestBody @Valid WashingMachineClothes washingMachineClothes, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        washingMachineClothesService.addWashingMachineClothes(washingMachineClothes);
        return ResponseEntity.status(200).body(new ApiResponse("Washing machine clothes added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateWashingMachineClothes(@PathVariable Integer id,@RequestBody @Valid WashingMachineClothes washingMachineClothes, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.badRequest().body(errors.getFieldError().getDefaultMessage());
        }
        washingMachineClothesService.updateWashingMachineClothes(id, washingMachineClothes);
        return ResponseEntity.ok(new ApiResponse("Washing machine clothes updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteWashingMachineClothes(@PathVariable Integer id){
        washingMachineClothesService.deleteWashingMachineClothes(id);
        return ResponseEntity.ok(new ApiResponse("Washing machine clothes deleted"));
    }

    @GetMapping("/validate-pin/washing-machine-clothes/{washingMachineClothesId}/pin/{pin}")
    public ResponseEntity validatePin(@PathVariable Integer washingMachineClothesId, @PathVariable String pin) {
        washingMachineClothesService.validatePin(washingMachineClothesId, pin);
        return ResponseEntity.status(200).body(new ApiResponse("Washing machine Clothes pin validated, you can use the machine "));
    }

}
