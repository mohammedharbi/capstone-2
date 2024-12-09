package com.example.autowash.Controller;

import com.example.autowash.ApiResponse.ApiResponse;
import com.example.autowash.Model.AutoWashCar;
import com.example.autowash.Service.AutoWashCarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auto-wash-car")
@RequiredArgsConstructor
public class AutoWashCarController {

    private final AutoWashCarService autoWashCarService;

    @GetMapping("/get")
    public ResponseEntity getAutoWashCar() {
        return ResponseEntity.status(200).body(autoWashCarService.getAllAutoWashCar());
    }

    @PostMapping("/add")
    public ResponseEntity addAutoWashCar(@RequestBody @Valid AutoWashCar autoWashCar, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        autoWashCarService.addAutoWashAutoWashCar(autoWashCar);
        return ResponseEntity.status(200).body(new ApiResponse("Auto wash car added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateAutoWashCar(@PathVariable Integer id, @RequestBody @Valid AutoWashCar autoWashCar, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        autoWashCarService.updateAutoWashCar(id, autoWashCar);
        return ResponseEntity.status(200).body(new ApiResponse("Auto wash car updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAutoWashCar(@PathVariable Integer id) {
        autoWashCarService.deleteAutoWashCar(id);
        return ResponseEntity.status(200).body(new ApiResponse("Auto wash car deleted"));
    }

    @GetMapping("/near-auto-wash-car/userId/{userId}/keyword/{keyword}")
    public ResponseEntity nearAutoWashCar(@PathVariable Integer userId,@PathVariable String keyword) {
        return ResponseEntity.status(200).body(autoWashCarService.nearAutoWashCar(userId,keyword));
    }

    @GetMapping("/open-auto-wash-car/{userId}")
    public ResponseEntity openAutoWashCar(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(autoWashCarService.openAutoWashCar(userId));
    }

    @PutMapping("/change-open-or-closed/{autoWashCarId}")
    public ResponseEntity changeOpenOrClosed(@PathVariable Integer autoWashCarId) {
        autoWashCarService.changeOpenOrClosed(autoWashCarId);
        return ResponseEntity.status(200).body(new ApiResponse("Auto wash car status changed"));
    }
}
