package com.example.autowash.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AutoWashCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name is empty")
    @Size(min = 4, max = 50, message = "name must be between 4 and 50")
    @Column(columnDefinition = "varchar(50) not null unique")
    private String name;

    @NotEmpty(message = "status is empty")
    @Pattern(regexp = "^(Open|Busy|Closed)$", message = "type must be 'Open' or 'Busy' or 'Closed'")
    @Column(columnDefinition = "varchar(6) not null")
    private String status;

    @NotNull(message = "orderCapacity is null")
    @Column(columnDefinition = "int not null")
    private Integer orderCapacity;

    @NotEmpty(message = "location is empty")
    @Size(min = 5,max = 100, message = "location must be between 5 & 100 characters")
    @Column(columnDefinition = "varchar(100)")
    private String location;

    @Max(5)
    @Column(columnDefinition = "double default 0.00")
    private Double avgScore=0.00;
}
