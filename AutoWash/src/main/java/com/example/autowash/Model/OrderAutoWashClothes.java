package com.example.autowash.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderAutoWashClothes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int ")
    private Integer userId;

    @Column(columnDefinition = "int ")
    private Integer autoWashClothesId;

    @NotNull(message = "Service ID is empty")
    @Column(columnDefinition = "int not null ")
    private Integer clothesServiceMenuId;

    // no need for @NotNull because when a user create a new order it will be assigned with an available washing machine
    @Column(columnDefinition = "int not null ")
    private Integer washingMachineClothesId;

    @Column(nullable = false, length = 50)
    // the service will assign the status @NotEmpty(message = "Status is required")
    @Pattern(regexp = "^(Pending|Completed|Cancelled)$", message = "Status must be 'Pending', 'Completed', or 'Cancelled'")
    private String status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private Double totalCost;


}