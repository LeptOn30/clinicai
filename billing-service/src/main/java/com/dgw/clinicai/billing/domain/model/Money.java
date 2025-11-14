package com.dgw.clinicai.billing.domain.model;

import lombok.Value;
import java.math.BigDecimal;
import java.util.Currency;

@Value
public class Money {
    private static final Currency USD = Currency.getInstance("USD");

    BigDecimal amount;
    Currency currency;

    public Money(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount.setScale(2), USD);
    }

    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) throw new IllegalArgumentException("Cannot add money with different currencies.");
        return new Money(this.amount.add(other.amount), this.currency);
    }
}