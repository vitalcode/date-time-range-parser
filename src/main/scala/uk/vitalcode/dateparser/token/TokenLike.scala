package uk.vitalcode.dateparser.token

import java.time.{DayOfWeek, LocalDate, LocalTime}

import scala.util.Try

trait TokenLike {
  val index: Int
}

trait TokenCompanion[T] {
  def of(text: String, index: Int): Try[T]
}

final case class Day(value: Int, index: Int = 0) extends TokenLike

final case class DateRange(from: LocalDate, to: LocalDate, index: Int) extends TokenLike

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

final case class Range(index: Int = 0) extends TokenLike

final case class TimeRange(from: LocalTime, to: LocalTime, index: Int) extends TokenLike

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
