package com.example.moduleproject.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table( name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private String brand;
    private Integer year;
}
