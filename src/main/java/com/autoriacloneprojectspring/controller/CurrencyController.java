package com.autoriacloneprojectspring.controller;

import com.autoriacloneprojectspring.dto.CurrencyRateResponseDto;
import com.autoriacloneprojectspring.services.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
    @GetMapping("/currency")
    public List<CurrencyRateResponseDto> getCurrencyRates() {
        return currencyService.getCurrencyRates();
    }

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24)
    @GetMapping("/UpdatePrice")
    public ResponseEntity<String> updatePrices() {
        currencyService.updatePricesAccordingToCurrencyRate();
        return ResponseEntity.ok("Prices was successfully updated");
    }
}
