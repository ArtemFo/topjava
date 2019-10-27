package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenDate(LocalDate current, LocalDate start, LocalDate end) {
        return current.compareTo(start) >= 0 && current.compareTo(end) <= 0;
    }

    public static boolean isBetweenTime(LocalTime current, LocalTime start, LocalTime end) {
        return current.compareTo(start) >= 0 && current.compareTo(end) <= 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

