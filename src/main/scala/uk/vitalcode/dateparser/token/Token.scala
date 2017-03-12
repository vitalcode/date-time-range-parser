package uk.vitalcode.dateparser.token

import java.time.{DayOfWeek, LocalDate, LocalTime}

trait Token {
  val index: Int
}

final case class Month(value: Int, index: Int = 0) extends Token

final case class Day(value: Int, index: Int = 0) extends Token

final case class Year(value: Int, index: Int = 0) extends Token

final case class Date(value: LocalDate, index: Int) extends Token

object Date {

  def apply(year: Int, month: Int, day: Int, index: Int = 0): Date = {
    Date(LocalDate.of(year, month, day), index)
  }
}

final case class DateRange(from: LocalDate, to: LocalDate, index: Int) extends Token

object DateRange {
  def apply(range: ((Int, Int, Int), (Int, Int, Int)), index: Int = 0): DateRange = {
    val (from, to) = range
    DateRange(
      LocalDate.of(from._1, from._2, from._3),
      LocalDate.of(to._1, to._2, to._3),
      index
    )
  }
}

final case class Range(index: Int = 0) extends Token

final case class TimeRange(from: LocalTime, to: LocalTime, index: Int) extends Token

object TimeRange {
  def apply(range: ((Int, Int), (Int, Int)), index: Int = 0): TimeRange = {
    val (from, to) = range
    TimeRange(
      LocalTime.of(from._1, from._2),
      LocalTime.of(to._1, to._2),
      index
    )
  }
}

final case class WeekDay(value: DayOfWeek, index: Int = 0) extends Token