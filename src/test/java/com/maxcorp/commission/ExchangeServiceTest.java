package com.maxcorp.commission;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExchangeServiceTest {

    @Test
    void shouldConvertToEUR() {
        // given
        var currency = "CHF";
        var date = "2022-03-02";
        var amount = "500.0";

        // when
        var convertedAmount = ExchangeService.convertToEUR(currency, date, amount);

        // then
        assertEquals(512.034663, convertedAmount);
    }
}