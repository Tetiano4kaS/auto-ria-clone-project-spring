package com.autoriacloneprojectspring.repository;

import com.autoriacloneprojectspring.entity.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    Optional<CurrencyRate> findByCurrency(String currency);
}
