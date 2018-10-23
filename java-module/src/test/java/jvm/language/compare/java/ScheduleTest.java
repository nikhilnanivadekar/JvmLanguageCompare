package jvm.language.compare.java;

import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;

import static java.time.Month.OCTOBER;
import static java.time.format.FormatStyle.SHORT;
import static java.time.temporal.ChronoField.*;
import static java.util.Locale.US;

public class ScheduleTest {
    @Test
    public void jvmLanguagesCompareJava() {
        String expected = "Wednesday, 10/24/18 2:30 PM - 3:15 PM | Moscone West - Room 2011";
        LocalDateTime dateTime = LocalDateTime.of(2018, OCTOBER, 24, 14, 30);
        ZoneId zoneId = ZoneId.of("America/Los_Angeles");
        ZonedDateTime zonedStartDateTime = dateTime.atZone(zoneId);
        ZonedDateTime zonedEndDateTime = zonedStartDateTime.plusMinutes(45);
        String room = "Moscone West - Room 2011";
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(SHORT);
        DateTimeFormatter endFormatter = new DateTimeFormatterBuilder()
                .appendValue(CLOCK_HOUR_OF_AMPM)
                .appendPattern(":")
                .appendValue(MINUTE_OF_HOUR)
                .appendPattern(" ")
                .appendText(AMPM_OF_DAY, TextStyle.SHORT).toFormatter();
        Assert.assertEquals(
                expected,
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, US) +
                        ", " + formatter.format(zonedStartDateTime) +
                        " - " + endFormatter.format(zonedEndDateTime) +
                        " | " + room);
    }
}
