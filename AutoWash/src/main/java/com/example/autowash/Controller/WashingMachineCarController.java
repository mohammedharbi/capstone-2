package com.example.autowash.Controller;

import com.example.autowash.ApiResponse.ApiResponse;
import com.example.autowash.Model.WashingMachineCar;
import com.example.autowash.Service.WashingMachineCarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/washing-machine-car")
@RequiredArgsConstructor
public class WashingMachineCarController {

    private final WashingMachineCarService washingMachineCarService;

    @GetMapping("/get")
    private ResponseEntity getAllWashingMachineCars() {
        return ResponseEntity.status(200).body(washingMachineCarService.getWashingMachinesCar());
    }

    @PostMapping("/add")
    public ResponseEntity addWashingMachineCar(@RequestBody @Valid WashingMachineCar washingMachineCar, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        washingMachineCarService.addWashingMachineCar(washingMachineCar);
        return ResponseEntity.status(200).body(new ApiResponse("Washing machine Car added "));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateWashingMachineCar(@PathVariable Integer id,@RequestBody @Valid WashingMachineCar washingMachineCar, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        washingMachineCarService.updateWashingMachineCar(id, washingMachineCar);
        return ResponseEntity.status(200).body(new ApiResponse("Washing machine Car updated "));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteWashingMachineCar(@PathVariable Integer id) {
        washingMachineCarService.deleteWashingMachineCar(id);
        return ResponseEntity.status(200).body(new ApiResponse("Washing machine Car deleted "));
    }

    @GetMapping("/validate-pin/washing-machine-car/{washingMachineCarId}/pin/{pin}")
    public ResponseEntity validatePin(@PathVariable Integer washingMachineCarId, @PathVariable String pin) {
        washingMachineCarService.validatePin(washingMachineCarId, pin);
        return ResponseEntity.status(200).body(new ApiResponse("Washing machine Car pin validated, you can use the machine "));
    }

}
