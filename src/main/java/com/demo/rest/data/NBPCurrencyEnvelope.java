package com.demo.rest.data;

import lombok.Data;

@Data
public class NBPCurrencyEnvelope {
    private String table;
    private String currency;
    private String code;
    private Rate[] rates;
}
