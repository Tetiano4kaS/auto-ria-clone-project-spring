package com.example.moduleproject.mapper;

import com.example.moduleproject.constant.Currency;
import com.example.moduleproject.dto.AdvertisementRequestDto;
import com.example.moduleproject.dto.CurrencyCalculatedDto;
import com.example.moduleproject.entity.Advertisement;
import com.example.moduleproject.entity.CalculatedCurrency;
import com.example.moduleproject.entity.Car;
import com.example.moduleproject.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdvertisementMapper {
    public static Advertisement mapAdvertisementRequestDtoToEntity(AdvertisementRequestDto dto, User user, Car car, CalculatedCurrency calculatedCurrency) {
        Advertisement advertisement = new Advertisement();
        advertisement.setCreationDate(LocalDateTime.now());
        advertisement.setPrice(dto.getPrice());
        advertisement.setCurrency(Currency.valueOf(dto.getCurrency()));
        advertisement.setDescription(dto.getDescription());
        advertisement.setUser(user);
        advertisement.setCar(car);
        advertisement.setCalculatedCurrency(calculatedCurrency);
        return advertisement;
    }


}
