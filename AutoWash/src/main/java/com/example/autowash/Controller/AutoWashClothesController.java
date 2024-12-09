package com.example.autowash.Controller;

import com.example.autowash.ApiResponse.ApiResponse;
import com.example.autowash.Model.AutoWashClothes;
import com.example.autowash.Service.AutoWashClothesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auto-wash-clothes")
@RequiredArgsConstructor
public class AutoWashClothesController {

    private final AutoWashClothesService autoWashClothesService;

    @GetMapping("/get")
    public ResponseEntity getAutoWashClothes() {
        return ResponseEntity.status(200).body(autoWashClothesService.getAllAutoWashClothes());
    }

    @PostMapping("/add")
    public ResponseEntity addAutoWashClothes(@RequestBody @Valid AutoWashClothes autoWashClothes, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        autoWashClothesService.addAutoWashAutoWashClothes(autoWashClothes);
        return ResponseEntity.status(200).body(new ApiResponse("Auto wash Clothes added"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateAutoWashClothes(@PathVariable Integer id, @RequestBody @Valid AutoWashClothes autoWashClothes, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        autoWashClothesService.updateAutoWashClothes(id, autoWashClothes);
        return ResponseEntity.status(200).body(new ApiResponse("Auto wash Clothes updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteAutoWashClothes(@PathVariable Integer id) {
        autoWashClothesService.deleteAutoWashClothes(id);
        return ResponseEntity.status(200).body(new ApiResponse("Auto wash Clothes deleted"));
    }

    @GetMapping("/near-auto-wash-clothes/userId/{userId}/keyword/{keyword}")
    public ResponseEntity nearAutoWashClothes(@PathVariable Integer userId,@PathVariable String keyword) {
        return ResponseEntity.status(200).body(autoWashClothesService.nearAutoWashClothes(userId,keyword));
    }

    @GetMapping("/open-auto-wash-clothes/{userId}")
    public ResponseEntity openAutoWashClothes(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(autoWashClothesService.openAutoWashClothes(userId));
    }

    @PutMapping("/change-open-or-closed/{autoWashClothesId}")
    public ResponseEntity changeOpenOrClosed(@PathVariable Integer autoWashClothesId) {
        autoWashClothesService.changeOpenOrClosed(autoWashClothesId);
        return ResponseEntity.status(200).body(new ApiResponse("Auto wash clothes status changed"));
    }
}
