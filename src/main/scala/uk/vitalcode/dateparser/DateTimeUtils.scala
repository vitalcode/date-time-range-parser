package uk.vitalcode.dateparser

import java.time.{DayOfWeek, LocalDate, LocalDateTime}

object DateTimeUtils {

  def datesInRange(from: LocalDate, to: LocalDate, weekDays: Set[DayOfWeek] = Set.empty, dates: List[LocalDate] = Nil): List[LocalDate] = {
    if (to.isBefore(from)) dates
    else {
      val newDates = if (weekDays.isEmpty || weekDays(to.getDayOfWeek)) to :: dates else dates
      datesInRange(from, to.minusDays(1), weekDays, newDates)
    }
  }

  def getYearForNextMonthAndDay(month: Int, day: Int, currentTime: LocalDateTime = (new DefaultDateTimeProvider).now): Int = {
    val currentMonth = currentTime.getMonth.getValue
    val currentDay = currentTime.getDayOfMonth
    if (currentMonth == month && currentDay == day) currentTime.getYear
    else getYearForNextMonthAndDay(month, day, currentTime.plusDays(1))
  }
}

trait DateTimeProvider {
  def now: LocalDateTime
}

class DefaultDateTimeProvider extends DateTimeProvider {
  override def now: LocalDateTime = LocalDateTime.now
}

