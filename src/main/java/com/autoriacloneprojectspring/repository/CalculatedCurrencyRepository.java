package com.autoriacloneprojectspring.repository;

import com.autoriacloneprojectspring.entity.CalculatedCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CalculatedCurrencyRepository extends JpaRepository<CalculatedCurrency, Integer> {
}
