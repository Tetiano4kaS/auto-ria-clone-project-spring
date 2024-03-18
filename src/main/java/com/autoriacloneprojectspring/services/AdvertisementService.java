package com.autoriacloneprojectspring.services;

import com.autoriacloneprojectspring.constant.AccountType;
import com.autoriacloneprojectspring.constant.Currency;
import com.autoriacloneprojectspring.dto.AdvertisementRequestDto;
import com.autoriacloneprojectspring.dto.CurrencyCalculatedDto;
import com.autoriacloneprojectspring.dto.CurrencyForCalculationDto;
import com.autoriacloneprojectspring.entity.Advertisement;
import com.autoriacloneprojectspring.entity.CalculatedCurrency;
import com.autoriacloneprojectspring.entity.Car;
import com.autoriacloneprojectspring.entity.User;
import com.autoriacloneprojectspring.exceptions.LimitAdvertisementBasicAccountException;
import com.autoriacloneprojectspring.exceptions.UserNotFoundException;
import com.autoriacloneprojectspring.mapper.AdvertisementMapper;
import com.autoriacloneprojectspring.mapper.CarMapper;
import com.autoriacloneprojectspring.mapper.CurrencyMapper;
import com.autoriacloneprojectspring.repository.AdvertisementRepository;
import com.autoriacloneprojectspring.repository.CalculatedCurrencyRepository;
import com.autoriacloneprojectspring.repository.CarRepository;
import com.autoriacloneprojectspring.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertisementService {
    private final AdvertisementRepository advertisementRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;
    private final CurrencyService currencyService;
    private final CalculatedCurrencyRepository calculatedCurrencyRepository;

    @Transactional
    public void createAdvertisement(AdvertisementRequestDto advertisementRequestDto) {
        String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("User with email: " + userEmail + "not found"));

        if (user.getAccountType() == AccountType.BASIC && advertisementRepository.countByUser(user) >= 1) {
            throw new LimitAdvertisementBasicAccountException("Basic account allows only one advertisement!");
        }

        Car car = CarMapper.mapAdvertisementRequestDtoToCarEntity(advertisementRequestDto);
        carRepository.save(car);

        CurrencyForCalculationDto currencyForCalculationDto = CurrencyMapper.mapAdvertisementRequestDtoToCurrencyForCalculationDto(advertisementRequestDto);
        CurrencyCalculatedDto currencyCalculatedDto = currencyService.calculateExchangeCurrencies(currencyForCalculationDto);

        CalculatedCurrency calculatedCurrency = CurrencyMapper.mapCurrencyCalculatedDtoToCalculatedCurrencyEntity(currencyCalculatedDto, Currency.valueOf(advertisementRequestDto.getCurrency()));
        calculatedCurrencyRepository.save(calculatedCurrency);

        Advertisement advertisement = AdvertisementMapper.mapAdvertisementRequestDtoToEntity(advertisementRequestDto, user, car, calculatedCurrency);
        advertisementRepository.save(advertisement);
    }

    public List<Advertisement> findAllAdvertisements() {
        return advertisementRepository.findAll();
    }

    public void deleteByAdvertisementId(long id) {
        advertisementRepository.deleteById(id);
    }
}
