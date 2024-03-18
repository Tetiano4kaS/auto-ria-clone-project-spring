package com.example.moduleproject.dto;

import com.example.moduleproject.constant.Currency;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementRequestDto {
    @NotNull
    @NotBlank
    private String carModel;
    private String carBrand;
    private Integer carYear;
    @NotEmpty
    @NotNull
    private Double price;
    @NotBlank
    @NotNull
    @Enumerated(EnumType.STRING)
    private String currency;
    private String description;
}
