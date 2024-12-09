package com.example.autowash.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WashingMachineCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "autoWashCar Id is null")
    @Column(columnDefinition = "int not null")
    private Integer autoWashCarId;

    @NotEmpty(message = "name is empty")
    @Size(min = 2, message = "name minimum is 1 characters")
    @Column(columnDefinition = "varchar(100)")
    private String name;

    @NotEmpty(message = "washing machine is empty")
    @Pattern(regexp = "^(Available|Busy)$")
    @Column(columnDefinition = "")
    private String washingMachineStatus;

    private String pin= "0000"; // it will be generated randomly when the client order any service

    private LocalDateTime createdAt;
}
