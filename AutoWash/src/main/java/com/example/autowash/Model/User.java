package com.example.autowash.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Check(constraints = "balance >= 0")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Full name is empty")
    @Size(min = 4, max = 20, message = "Full name must be between 4 and 20 characters")
    @Column(columnDefinition = "varchar(20) not null")
    private String fullName;

    @NotEmpty(message = "username is empty")
    @Size(min = 4,max = 20, message = "Username must be between 4 and 20 characters")
    @Column(columnDefinition = "varchar(30) not null unique")
    private String username;

    @NotEmpty(message = "password is empty")
    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters")
    @Column(columnDefinition = "varchar(20) not null")
    private String password;

    @NotEmpty(message = "email is empty")
    @Email(message = "Email must be a valid email")
    @Size(max = 40, message = "Email must be max 40 characters")
    @Column(columnDefinition = "varchar(40) not null unique")
    private String email;

    @NotEmpty(message = "phone is empty")
    @Pattern(regexp = "^(\\+966|0)?5\\d{8}$")
    @Column(columnDefinition = "varchar(13) not null unique")
    private String phone;

    @PositiveOrZero(message = "Balance must be a positive number")
    @Column(columnDefinition = "double default 0 not null")
    private Double balance= 0.0;

    private Integer userOrders=0;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime registrationDate;
}
