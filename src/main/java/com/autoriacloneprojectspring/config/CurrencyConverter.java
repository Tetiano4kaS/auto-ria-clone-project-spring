package com.example.moduleproject.config;

import com.example.moduleproject.constant.Currency;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CurrencyConverter implements AttributeConverter<Currency, String> {

    @Override
    public String convertToDatabaseColumn(Currency currency) {
        return (currency == null) ? null : currency.name();
    }

    @Override
    public Currency convertToEntityAttribute(String code) {
        return (code == null) ? null : Currency.valueOf(code);
    }
}
