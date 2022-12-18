package com.demo.rest.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrayEnvelopeTest {

    private ArrayList<Double> list;

    @BeforeEach
    void setUp() {
        this.list = new ArrayList<>(List.of(1.42, 5.12, 4.21, 8.11));
    }

    @Test
    void sortArrayAscending() {
        ArrayEnvelope arrayEnvelope = new ArrayEnvelope("ASC", this.list);
        arrayEnvelope.sortArray();
        this.list.sort(Double::compare);
        assertEquals(this.list, arrayEnvelope.getArray());
    }

    @Test
    void sortArrayDescending() {
        ArrayEnvelope arrayEnvelope = new ArrayEnvelope("DESC", this.list);
        arrayEnvelope.sortArray();
        this.list.sort((x1, x2) -> Double.compare(x2, x1));
        assertEquals(this.list, arrayEnvelope.getArray());
    }
}