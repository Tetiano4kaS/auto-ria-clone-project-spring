package com.autoriacloneprojectspring.entity;

import com.autoriacloneprojectspring.constant.Currency;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "calculatedCurrency")
public class CalculatedCurrency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Enumerated(EnumType.STRING)
    private Currency calculatedCurrency1;

    private Double calculatedPrice1;

    @Enumerated(EnumType.STRING)
    private Currency calculatedCurrency2;

    private Double calculatedPrice2;
}
