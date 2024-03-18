package com.autoriacloneprojectspring.entity;

import com.autoriacloneprojectspring.constant.Currency;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "advertisements")
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime creationDate;

    @Column(name = "price_by_user")
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_by_user")
    private Currency currency;

    private String description;

    @OneToOne
    @JoinColumn(name = "calculatedCurrency_id", nullable = false)
    private CalculatedCurrency calculatedCurrency;

    @OneToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
