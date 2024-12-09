package com.example.autowash.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CarServiceMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "autoWash Id is null")
    @Column(columnDefinition = "int not null")
    private Integer autoWashCarId;

    @NotEmpty(message = "Service name is empty")
    @Size(min = 4, max = 30, message = "Service name must be between 4 and 30 characters")
    @Column(columnDefinition = "varchar(30) not null unique")
    private String serviceName;

    @Size(max = 100, message = "Description max 100 characters")
    @Column(columnDefinition = "varchar(100)")
    private String description;

    @NotNull(message = "service duration is null")
    @Min(value = 1,message = "service duration minimum is 1 minute")
    @Column(columnDefinition = "int not null")
    private Integer serviceDurationMinutes; //in minutes

    @NotNull(message = "Price is empty")
    @Min(1)
    @Column(columnDefinition = "double not null")
    private Double price;
}
