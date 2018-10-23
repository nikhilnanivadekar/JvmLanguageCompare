package jvm.language.compare.scala

import java.time.format.FormatStyle._
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder, TextStyle}
import java.time.temporal.ChronoField._
import java.time.{LocalDateTime, Month, ZoneId}
import java.util.Locale._

import org.junit.Test

class ScheduleTest {
  @Test def jvmLanguagesCompareScala(): Unit = {
    val expected = "Wednesday, 10/24/18 2:30 PM - 3:15 PM | Moscone West - Room 2011"
    val dateTime = LocalDateTime.of(2018, Month.OCTOBER, 24, 14, 30)
    val zoneId = ZoneId.of("America/Los_Angeles")
    val zonedStartDateTime = dateTime.atZone(zoneId)
    val zonedEndDateTime = zonedStartDateTime.plusMinutes(45)
    val room = "Moscone West - Room 2011"
    val formatter = DateTimeFormatter.ofLocalizedDateTime(SHORT)
    val endFormatter = new DateTimeFormatterBuilder()
      .appendValue(CLOCK_HOUR_OF_AMPM)
      .appendPattern(":")
      .appendValue(MINUTE_OF_HOUR)
      .appendPattern(" ")
      .appendText(AMPM_OF_DAY, TextStyle.SHORT)
      .toFormatter
    assert(
      expected ==
      dateTime.getDayOfWeek.getDisplayName(TextStyle.FULL, US)
        + ", " + formatter.format(zonedStartDateTime)
        + " - " + endFormatter.format(zonedEndDateTime)
        + " | " + room)
  }
}
