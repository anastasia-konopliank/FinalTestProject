package it_academy.utils;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

public class SmartOrderedList {
    private final String firstValue;
    private final String[] values;

    public SmartOrderedList(String[] values) {
        firstValue = values[0];
        this.values = values;
    }

    public String[] order() {

        try {
            Double.parseDouble(firstValue);
            return sortAsDouble(values);
        } catch (Exception e) {
            try {
                LocalDateTime.parse(firstValue);
                return sortAsDateTimes(values);
            } catch (Exception ex) {
                return Arrays.stream(values).sorted().toArray(String[]::new);
            }
        }
    }

    private String[] sortAsDouble(String[] values) {
        return Arrays.stream(values).sorted((o1, o2) -> {
            var delta = Double.parseDouble(o1) - Double.parseDouble(o2);

            if (Math.abs(delta) < 0.00001) {
                return 0;
            }

            if (delta > 0) {
                return 1;
            }

            return -1;
        }).toArray(String[]::new);
    }

    private String[] sortAsDateTimes(String[] values) {
        return Arrays.stream(values).sorted((o1, o2) -> {
            var delta = Duration.between(LocalDateTime.parse(o1), LocalDateTime.parse(o2));

            if (delta.getSeconds() == 0) {
                return 0;
            } else if (delta.getSeconds() > 0) {
                return 1;
            } else {
                return -1;
            }
        }).toArray(String[]::new);
    }
}
