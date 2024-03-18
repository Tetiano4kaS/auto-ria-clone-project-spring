package com.example.moduleproject.repository;

import com.example.moduleproject.constant.Currency;
import com.example.moduleproject.entity.CalculatedCurrency;
import com.example.moduleproject.entity.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CalculatedCurrencyRepository extends JpaRepository<CalculatedCurrency, Integer> {

}
