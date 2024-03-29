package com.autoriacloneprojectspring.services;

import com.autoriacloneprojectspring.constant.Currency;
import com.autoriacloneprojectspring.dto.AdvertisementRequestDto;
import com.autoriacloneprojectspring.dto.CurrencyCalculatedDto;
import com.autoriacloneprojectspring.dto.CurrencyForCalculationDto;
import com.autoriacloneprojectspring.dto.CurrencyRateResponseDto;
import com.autoriacloneprojectspring.entity.Advertisement;
import com.autoriacloneprojectspring.entity.CalculatedCurrency;
import com.autoriacloneprojectspring.entity.CurrencyRate;
import com.autoriacloneprojectspring.mapper.CurrencyMapper;
import com.autoriacloneprojectspring.repository.AdvertisementRepository;
import com.autoriacloneprojectspring.repository.CalculatedCurrencyRepository;
import com.autoriacloneprojectspring.repository.CurrencyRateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private static String API_URL = "https://api.privatbank.ua/p24api/pubinfo?json&exchange&coursid=5";
    private final CurrencyRateRepository currencyRateRepository;
    private final AdvertisementRepository advertisementRepository;
    private final CalculatedCurrencyRepository calculatedCurrencyRepository;
    private final RestTemplate restTemplate;

    @Transactional
    public List<CurrencyRateResponseDto> getCurrencyRates() {
        currencyRateRepository.deleteAll();
        CurrencyRateResponseDto[] currencyRatesDtos = restTemplate.getForObject(API_URL, CurrencyRateResponseDto[].class);
        List<CurrencyRateResponseDto> currencyRateResponseDtos = Arrays.asList(currencyRatesDtos);
        List<CurrencyRate> currencyRatesList = CurrencyMapper.mapCurrencyRateResponseDtoToCurrencyRate(currencyRateResponseDtos);
        currencyRateRepository.saveAll(currencyRatesList);
        return currencyRateResponseDtos;
    }

    public CurrencyCalculatedDto calculateExchangeCurrencies(CurrencyForCalculationDto currencyForCalculationDto) {
        CurrencyCalculatedDto currencyCalculatedDto = new CurrencyCalculatedDto();
        CurrencyRate currencyRateEUR = currencyRateRepository.findByCurrency(String.valueOf(Currency.EUR)).orElseThrow(() ->
                new RuntimeException(" Error during currency exchange EUR calculation"));
        CurrencyRate currencyRateUSD = currencyRateRepository.findByCurrency(String.valueOf(Currency.USD)).orElseThrow(() ->
                new RuntimeException(" Error during currency exchange USD calculation"));

        switch (currencyForCalculationDto.getCurrency()) {

            case UAH:
                double priceUAH = currencyForCalculationDto.getPrice();
                currencyCalculatedDto.setPriceUAH(priceUAH);
                currencyCalculatedDto.setPriceUSD(priceUAH / currencyRateUSD.getSale());
                currencyCalculatedDto.setPriceEUR(priceUAH / currencyRateEUR.getSale());
                break;

            case EUR:
                double priceEUR = currencyForCalculationDto.getPrice();
                double priceHrn = priceEUR * currencyRateEUR.getBuy();
                currencyCalculatedDto.setPriceUAH(priceHrn);
                currencyCalculatedDto.setPriceUSD(priceHrn / currencyRateUSD.getSale());
                currencyCalculatedDto.setPriceEUR(priceEUR);
                break;

            case USD:
                double priceUSD = currencyForCalculationDto.getPrice();
                double hrn = priceUSD * currencyRateUSD.getBuy();
                currencyCalculatedDto.setPriceUAH(hrn);
                currencyCalculatedDto.setPriceUSD(priceUSD);
                currencyCalculatedDto.setPriceEUR(hrn / currencyRateEUR.getSale());
                break;

            default:
                break;
        }
        return currencyCalculatedDto;
    }

    @Transactional
    public void updatePricesAccordingToCurrencyRate() {
        List<Advertisement> advertisementList = advertisementRepository.findAll();
        if (!advertisementList.isEmpty()) {
            CurrencyForCalculationDto currencyForCalculationDto = new CurrencyForCalculationDto();
            calculatedCurrencyRepository.deleteAll();
            advertisementList.forEach((advertisement -> {
                Double price = advertisement.getPrice();
                Currency currency = advertisement.getCurrency();
                currencyForCalculationDto.setPrice(price);
                currencyForCalculationDto.setCurrency(currency);
                CurrencyCalculatedDto currencyCalculatedDto = calculateExchangeCurrencies(currencyForCalculationDto);
                AdvertisementRequestDto advertisementRequestDto = new AdvertisementRequestDto();
                advertisementRequestDto.setCurrency(String.valueOf(currency));
                CalculatedCurrency calculatedCurrency = CurrencyMapper.mapCurrencyCalculatedDtoToCalculatedCurrencyEntity(currencyCalculatedDto, Currency.valueOf(advertisementRequestDto.getCurrency()));
                calculatedCurrencyRepository.save(calculatedCurrency);
                advertisement.setCalculatedCurrency(calculatedCurrency);
                advertisementRepository.save(advertisement);
            }));
        }
    }
}
