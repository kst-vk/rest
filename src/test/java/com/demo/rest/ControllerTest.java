package com.demo.rest;

import com.demo.rest.data.ArrayEnvelope;
import com.demo.rest.data.CurrencyRequestEnvelope;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(classes = RestApplication.class)
@WebAppConfiguration
class ControllerTest {
    protected MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;
    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void pingPong() throws Exception {
        String uri = "/status/ping";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertEquals(content, "Pong");
    }

    @Test
    void sortArrayAscending() throws Exception {
        sortArray("ASC", Double::compare);
    }

    @Test
    void sortArrayDescending() throws Exception {
        sortArray("DESC", (x1, x2) -> Double.compare(x2, x1));
    }

    @Test
    void currencyValue() throws Exception {
        String uri = "/currencies/get-current-currency-value-command";
        CurrencyRequestEnvelope currencyRequestEnvelope = new CurrencyRequestEnvelope();
        currencyRequestEnvelope.setCurrency("EUR");
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(currencyRequestEnvelope))).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    private void sortArray(String order, Comparator<Double> comparator) throws Exception {
        String uri = "/numbers/sort-command";
        ArrayList<Double> inputArray = new ArrayList<>(List.of(3.12, 3.11, 2.11, 5.21));
        ArrayEnvelope inputArrayEnvelope = new ArrayEnvelope(order, inputArray);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(new ObjectMapper().writeValueAsString(inputArrayEnvelope))).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String json = mvcResult.getResponse().getContentAsString();
        ArrayEnvelope outputArrayEnvelope = new ObjectMapper().readValue(json, ArrayEnvelope.class);
        inputArray.sort(comparator);
        assertEquals(outputArrayEnvelope.getArray(), inputArray);
    }
}