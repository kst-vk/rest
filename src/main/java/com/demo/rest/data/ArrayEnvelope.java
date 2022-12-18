package com.demo.rest.data;

import lombok.Data;
import java.util.ArrayList;
import java.util.Objects;

@Data
public class ArrayEnvelope {
    ArrayList<Double> array;
    String order;

    public ArrayEnvelope(){};
    public ArrayEnvelope(String order, ArrayList<Double> i) {
        this.order = order;
        this.array = i;
    }

    public void sortArray() {
        if (this.array != null) {
            this.array.sort((o1, o2) -> {
                if (Objects.equals(this.order, "ASC")) {
                    return Double.compare(o1, o2);
                } else if (Objects.equals(this.order, "DESC")) {
                    return Double.compare(o2, o1);
                }
                return 0;
            });
        }
    }
}
