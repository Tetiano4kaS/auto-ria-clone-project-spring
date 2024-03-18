package com.autoriacloneprojectspring.mapper;

import com.autoriacloneprojectspring.constant.Currency;
import com.autoriacloneprojectspring.dto.AdvertisementRequestDto;
import com.autoriacloneprojectspring.entity.Advertisement;
import com.autoriacloneprojectspring.entity.CalculatedCurrency;
import com.autoriacloneprojectspring.entity.Car;
import com.autoriacloneprojectspring.entity.User;
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
