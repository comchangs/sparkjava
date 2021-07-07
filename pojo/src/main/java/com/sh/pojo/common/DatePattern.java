package com.sh.pojo.common;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DatePattern implements DateFormatter {

    private String localDate;

    private String localDateTime;

    public DatePattern(LocalDate localDate) {
        this.localDate = this.localDateFormat(localDate);
    }

    public DatePattern(LocalDateTime localDateTime) {
        this.localDateTime = this.localDateTimeFormat(localDateTime);
    }

    private String localDateFormat(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
    }

    private String localDateTimeFormat(LocalDateTime date) {
        return date.format(DateTimeFormatter.ofPattern("yyyy.MM.dd hh:mm:ss"));
    }

    private String getLocalDate() {
        return this.localDate;
    }

    private String getLocalDateTime() {
        return this.localDateTime;
    }

    @Override
    public String yearMonthDay() {
        return this.getLocalDate();
    }

    @Override
    public String yearMonthDayHourMMss() {
        return this.getLocalDateTime();
    }
}
