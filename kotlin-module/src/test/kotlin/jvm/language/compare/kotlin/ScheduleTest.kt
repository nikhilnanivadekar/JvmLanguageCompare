package jvm.language.compare.kotlin

import org.junit.Assert
import org.junit.Test
import java.time.Duration.ofMinutes
import java.time.LocalDateTime
import java.time.Month.OCTOBER
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.FormatStyle.SHORT
import java.time.format.TextStyle
import java.time.temporal.ChronoField.*
import java.util.*

class ScheduleTest {
    @Test
    fun jvmLanguagesCompareKotlin() {
        val expected = "Wednesday, 10/24/18 2:30 PM - 3:15 PM | Moscone West - Room 2011"
        val dateTime = LocalDateTime.of(2018, OCTOBER, 24, 14, 30)
        val zoneId = ZoneId.of("America/Los_Angeles")
        val zonedStartDateTime = dateTime.atZone(zoneId)
        val zonedEndDateTime = zonedStartDateTime + ofMinutes(45)
        val room = "Moscone West - Room 2011"
        val formatter = DateTimeFormatter.ofLocalizedDateTime(SHORT)
        val endFormatter = DateTimeFormatterBuilder()
                .appendValue(CLOCK_HOUR_OF_AMPM)
                .appendPattern(":")
                .appendValue(MINUTE_OF_HOUR)
                .appendPattern(" ")
                .appendText(AMPM_OF_DAY, TextStyle.SHORT).toFormatter()
        Assert.assertEquals(
                expected,
                dateTime.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.US) +
                        ", " + formatter.format(zonedStartDateTime) +
                        " - " + endFormatter.format(zonedEndDateTime) +
                        " | " + room)
    }
}
