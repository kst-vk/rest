package com.demo.rest.data;

import lombok.Data;

@Data
public class CurrencyResponseEnvelope {
    private Double value;
    public CurrencyResponseEnvelope(NBPCurrencyEnvelope env) {
        if (env != null) this.value = env.getRates()[0].getMid();
    }
}
