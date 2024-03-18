package com.autoriacloneprojectspring.mapper;

import com.autoriacloneprojectspring.constant.Currency;
import com.autoriacloneprojectspring.dto.AdvertisementRequestDto;
import com.autoriacloneprojectspring.dto.CurrencyCalculatedDto;
import com.autoriacloneprojectspring.dto.CurrencyForCalculationDto;
import com.autoriacloneprojectspring.dto.CurrencyRateResponseDto;
import com.autoriacloneprojectspring.entity.CalculatedCurrency;
import com.autoriacloneprojectspring.entity.CurrencyRate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyMapper {
    public static List<CurrencyRate> mapCurrencyRateResponseDtoToCurrencyRate(List<CurrencyRateResponseDto> dtoList) {
        List<CurrencyRate> currencyRateList = new ArrayList<>();

        dtoList.forEach(dto -> {
            CurrencyRate currencyRate = new CurrencyRate();
            currencyRate.setCurrency(dto.getCcy());
            currencyRate.setBuy(Double.valueOf(dto.getBuy()));
            currencyRate.setSale(Double.valueOf(dto.getSale()));
            currencyRate.setTimestamp(LocalDateTime.now());
            currencyRateList.add(currencyRate);
        });

        return currencyRateList;
    }

    public static CalculatedCurrency mapCurrencyCalculatedDtoToCalculatedCurrencyEntity(CurrencyCalculatedDto dto, @NotBlank @NotNull Currency currency) {
        CalculatedCurrency calculatedCurrency = new CalculatedCurrency();
        if (currency == Currency.UAH) {
            calculatedCurrency.setCalculatedCurrency1(Currency.EUR);
            calculatedCurrency.setCalculatedPrice1(dto.getPriceEUR());
            calculatedCurrency.setCalculatedCurrency2(Currency.USD);
            calculatedCurrency.setCalculatedPrice2(dto.getPriceUSD());
        }
        if (currency == Currency.USD) {
            calculatedCurrency.setCalculatedCurrency1(Currency.EUR);
            calculatedCurrency.setCalculatedPrice1(dto.getPriceEUR());
            calculatedCurrency.setCalculatedCurrency2(Currency.UAH);
            calculatedCurrency.setCalculatedPrice2(dto.getPriceUAH());
        }
        if (currency == Currency.EUR) {
            calculatedCurrency.setCalculatedCurrency1(Currency.USD);
            calculatedCurrency.setCalculatedPrice1(dto.getPriceUSD());
            calculatedCurrency.setCalculatedCurrency2(Currency.UAH);
            calculatedCurrency.setCalculatedPrice2(dto.getPriceUAH());
        }
        return calculatedCurrency;
    }

    public static CurrencyForCalculationDto mapAdvertisementRequestDtoToCurrencyForCalculationDto(AdvertisementRequestDto advertisementRequestDto) {
        CurrencyForCalculationDto currencyForCalculationDto = new CurrencyForCalculationDto();
        currencyForCalculationDto.setCurrency(Currency.valueOf(advertisementRequestDto.getCurrency()));
        currencyForCalculationDto.setPrice(advertisementRequestDto.getPrice());

        return currencyForCalculationDto;
    }
}
