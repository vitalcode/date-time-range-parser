package uk.vitalcode.dateparser

import java.time.DayOfWeek

trait DateToken {
  val index: Int
}

final case class Month(value: Int, index: Int = 0) extends DateToken

final case class Day(value: Int, index: Int = 0) extends DateToken

final case class Year(value: Int, index: Int = 0) extends DateToken

final case class Date(day: Int, month: Int, year: Int, index: Int = 0) extends DateToken

final case class DateRange(from: Date, to: Date, index: Int = 0) extends DateToken

final case class Time(value: Int, index: Int = 0) extends DateToken

final case class Range(index: Int = 0) extends DateToken

final case class TimeRange(from: Int, to: Int, index: Int = 0) extends DateToken

final case class WeekDay(value: DayOfWeek, index: Int = 0) extends DateToken