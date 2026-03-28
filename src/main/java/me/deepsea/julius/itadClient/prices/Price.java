package me.deepsea.julius.itadClient.prices;

import java.math.BigDecimal;

public record Price(
        BigDecimal amount,
        String currency
) {
}
