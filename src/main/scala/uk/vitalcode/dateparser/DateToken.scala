package uk.vitalcode.dateparser

trait DateToken {
  val index: Int
}

final case class Month(value: Int, index: Int = 0) extends DateToken

final case class Day(value: Int, index: Int = 0) extends DateToken

final case class Year(value: Int, index: Int = 0) extends DateToken

final case class Date(day: Int, month: Int, year: Int, index: Int = 0) extends DateToken

final case class Time(value: Int, index: Int = 0) extends DateToken

final case class Range(index: Int = 0) extends DateToken

final case class TimeRange(from: Int, to: Int, index: Int = 0) extends DateToken