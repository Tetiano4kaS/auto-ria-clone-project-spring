package com.example.moduleproject.services;

import com.example.moduleproject.constant.AccountType;
import com.example.moduleproject.constant.Currency;
import com.example.moduleproject.dto.AdvertisementRequestDto;
import com.example.moduleproject.dto.CurrencyCalculatedDto;
import com.example.moduleproject.dto.CurrencyForCalculationDto;
import com.example.moduleproject.entity.Advertisement;
import com.example.moduleproject.entity.CalculatedCurrency;
import com.example.moduleproject.entity.Car;
import com.example.moduleproject.entity.User;
import com.example.moduleproject.exceptions.LimitAdvertisementBasicAccountException;
import com.example.moduleproject.exceptions.UserNotFoundException;
import com.example.moduleproject.mapper.AdvertisementMapper;
import com.example.moduleproject.mapper.CarMapper;
import com.example.moduleproject.mapper.CurrencyMapper;
import com.example.moduleproject.repository.AdvertisementRepository;
import com.example.moduleproject.repository.CalculatedCurrencyRepository;
import com.example.moduleproject.repository.CarRepository;
import com.example.moduleproject.repository.UserRepository;
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
