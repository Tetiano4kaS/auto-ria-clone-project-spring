package com.autoriacloneprojectspring.mapper;

import com.autoriacloneprojectspring.dto.AdvertisementRequestDto;
import com.autoriacloneprojectspring.entity.Car;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarMapper {
    public static Car mapAdvertisementRequestDtoToCarEntity(AdvertisementRequestDto dto) {
        Car car = new Car();
        car.setBrand(dto.getCarBrand());
        car.setModel(dto.getCarModel());
        car.setYear(dto.getCarYear());
        return car;
    }
}
