package jvm.language.compare.groovy


import org.junit.Test

import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.TextStyle

import static java.time.Duration.ofMinutes
import static java.time.format.FormatStyle.SHORT
import static java.time.temporal.ChronoField.*
import static java.util.Locale.US

class ScheduleTest {
    @Test
    void jvmLanguagesCompareGroovy() {
        def expected = "Wednesday, 10/24/18 2:30 PM - 3:15 PM | Moscone West - Room 2011"
        def dateTime = LocalDateTime.of(2018, Month.OCTOBER, 24, 14, 30)
        def zoneId = ZoneId.of("America/Los_Angeles")
        def zonedStartDateTime = dateTime.atZone(zoneId)
        def zonedEndDateTime = zonedStartDateTime + ofMinutes(45)
        def room = "Moscone West - Room 2011"
        def formatter = DateTimeFormatter.ofLocalizedDateTime(SHORT)
        def endFormatter = new DateTimeFormatterBuilder()
                .appendValue(CLOCK_HOUR_OF_AMPM)
                .appendPattern(":")
                .appendValue(MINUTE_OF_HOUR)
                .appendPattern(" ")
                .appendText(AMPM_OF_DAY, TextStyle.SHORT).toFormatter()
        assert expected ==
                dateTime.getDayOfWeek().getDisplayName(TextStyle.FULL, US) +
                ", " + formatter.format(zonedStartDateTime) +
                " - " + endFormatter.format(zonedEndDateTime) +
                " | " + room
    }
}
