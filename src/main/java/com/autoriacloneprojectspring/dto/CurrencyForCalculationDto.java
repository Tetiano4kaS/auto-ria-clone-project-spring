package com.autoriacloneprojectspring.dto;

import com.autoriacloneprojectspring.constant.Currency;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class CurrencyForCalculationDto {
    @Enumerated(EnumType.STRING)
    private Currency currency;
    private Double price;
}
