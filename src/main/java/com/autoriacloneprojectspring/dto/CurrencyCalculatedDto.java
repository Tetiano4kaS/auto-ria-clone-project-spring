package com.example.moduleproject.dto;

import com.example.moduleproject.constant.Currency;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class CurrencyCalculatedDto {
    private double priceUAH;
    private double priceUSD;
    private double priceEUR;
}
