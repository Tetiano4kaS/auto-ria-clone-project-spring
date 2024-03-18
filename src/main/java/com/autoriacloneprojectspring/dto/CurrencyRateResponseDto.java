package com.autoriacloneprojectspring.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CurrencyRateResponseDto {
    private String ccy;
    @JsonProperty(value = "base_ccy")
    private String baseCcy;
    private String buy;
    private String sale;
}
