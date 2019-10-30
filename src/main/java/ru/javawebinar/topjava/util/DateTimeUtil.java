package ru.javawebinar.topjava.util;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final LocalDate MIN_DATE = LocalDate.of(0, 1, 1);
    public static final LocalDate MAX_DATE = LocalDate.of(3000, 1, 1);

    public static boolean isBetweenDate(LocalDate current, LocalDate start, LocalDate end) {
        return current.compareTo(start) >= 0 && current.compareTo(end) <= 0;
    }

    public static boolean isBetweenTime(LocalTime current, LocalTime start, LocalTime end) {
        return current.compareTo(start) >= 0 && current.compareTo(end) <= 0;
    }

    public static LocalDate parseLocalDate(String date, LocalDate defaultDate) {
        return StringUtils.isEmpty(date) ? defaultDate : LocalDate.parse(date);
    }

    public static LocalTime parseLocalTime(String time, LocalTime defaultTime) {
        return StringUtils.isEmpty(time) ? defaultTime : LocalTime.parse(time);
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

