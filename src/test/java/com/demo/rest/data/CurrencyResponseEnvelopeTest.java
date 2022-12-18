package com.demo.rest.data;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class CurrencyResponseEnvelopeTest {

    @Test
    void getValue() {
        Rate[] rates = {new Rate()};
        rates[0].setMid(3.12);
        NBPCurrencyEnvelope nbpCurrencyEnvelope = new NBPCurrencyEnvelope();
        nbpCurrencyEnvelope.setRates(rates);
        CurrencyResponseEnvelope currencyResponseEnvelope = new CurrencyResponseEnvelope(nbpCurrencyEnvelope);
        assertEquals(currencyResponseEnvelope.getValue(), 3.12);
    }
}