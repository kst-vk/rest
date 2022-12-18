package com.demo.rest;

import com.demo.rest.data.ArrayEnvelope;
import com.demo.rest.data.CurrencyRequestEnvelope;
import com.demo.rest.data.CurrencyResponseEnvelope;
import com.demo.rest.data.NBPCurrencyEnvelope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Controller {
    @GetMapping("/status/ping")
    public String pingPong() {
        return "Pong";
    }

    @PostMapping("/numbers/sort-command")
    public ArrayEnvelope sortArray(@RequestBody ArrayEnvelope env) {
        if (!env.getOrder().matches("ASC|DESC")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect order. The order can be either ascending (ASC) or descending (DESC)");
        }
        env.sortArray();
        return env;
    }

    @PostMapping("/currencies/get-current-currency-value-command")
    public CurrencyResponseEnvelope currencyValue(@RequestBody CurrencyRequestEnvelope env) {
        String url = String.format("http://api.nbp.pl/api/exchangerates/rates/A/%s/?format=json", env.getCurrency());
        RestTemplate restTemplate = new RestTemplate();
        NBPCurrencyEnvelope nbpResponse;
        try {
            nbpResponse = restTemplate.getForObject(url, NBPCurrencyEnvelope.class);
        } catch (RestClientException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid currency");
        }
        return new CurrencyResponseEnvelope(nbpResponse);
    }
}
