package org.max.spring.data.config.query.builder;

import com.fasterxml.jackson.annotation.JsonValue;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by Maksym_Bondarenko on 4/13/2016.
 */
public class Interval {
    public static DateTimeFormatter full = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
    public static DateTimeFormatter simple = DateTimeFormat.forPattern("yyyy-MM-dd");

    private final LocalDate from;
    private final LocalDate to;
    private DateTimeFormatter df = simple;

    public Interval(Date from, Date to) {
        this.from = new LocalDate(from);
        this.to = new LocalDate(to);
    }

    public Interval(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
    }

    public void fullDateFormat() {
        df = full;
    }

    public void setDataFormat(DateTimeFormatter df) {
        this.df = df;
    }

    @JsonValue
    @Override
    public String toString() {
        return df.print(from) + '/' + df.print(to);
    }
}
