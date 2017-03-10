package uk.vitalcode.dateparser

import java.time.{DayOfWeek, LocalDate}
import scala.util.Try

object DateTimeUtils {

  def datesInRange(from: LocalDate, to: LocalDate, weekDays: Set[DayOfWeek] = Set.empty, dates: List[LocalDate] = Nil): List[LocalDate] = {
    if (to.isBefore(from)) dates
    else {
      val newDates = if (weekDays.isEmpty || weekDays(to.getDayOfWeek)) to :: dates else dates
      datesInRange(from, to.minusDays(1), weekDays, newDates)
    }
  }
}