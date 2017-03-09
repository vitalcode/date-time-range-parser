package uk.vitalcode.dateparser

import java.time.{DayOfWeek, LocalDate}
import scala.util.Try

object DateTimeUtils {

  def datesInRange(from: LocalDate, to: LocalDate, dates: List[LocalDate]): List[LocalDate] = {
    if (to.isBefore(from)) dates
    else datesInRange(from, to.minusDays(1), to :: dates)
  }
}
