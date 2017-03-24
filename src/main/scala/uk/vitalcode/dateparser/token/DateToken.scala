package uk.vitalcode.dateparser.token

import java.time.{DayOfWeek, LocalDate, LocalTime}

import scala.util.{Failure, Success, Try}

trait DateToken {
  val index: Int
}

object DateToken {

  def of(token: String, index: Int): Try[DateToken] = {
    val week = WeekDay.of(token, index)
    if (week.isSuccess) week
    else {
      val time = Time.of(token, index)
      if (time.isSuccess) time
      else {
        val year = Year.of(token, index)
        if (year.isSuccess) year
        else {
          val month = Month.of(token, index)
          if (month.isSuccess) month
          else {
            val day = Day.of(token, index)
            if (day.isSuccess) day
            else {
              val range = Range.of(token, index)
              if (range.isSuccess) range
              else Failure(new Exception(s"[$token] cannot be parsed as a date token"))
            }
          }
        }
      }
    }
  }

  def parse(text: String): List[DateToken] = {
    // Split on white space or "-" (range character, including "-" as return token, but not before AM/PM)
    val splitRegEx =
    """(?<![-])[\s]+(?![-]|PM|pm|AM|am)|(?=[-,])|(?<=[-,])""".r

    splitRegEx.split(text).toList.filter(_.nonEmpty).zipWithIndex.flatMap {
      case (token, index) => DateToken.of(token, index) match {
        case Success(dateToken) =>
          List(dateToken)
        case _ =>
          Nil
      }
    }
  }
}

trait TokenCompanion[T] {
  def of(token: String, index: Int): Try[T]
}

final case class Date(value: LocalDate, index: Int) extends DateToken

object Date {

  def apply(year: Int, month: Int, day: Int, index: Int = 0): Try[Date] = Try {
    Date(LocalDate.of(year, month, day), index)
  }
}

final case class DateRange(from: LocalDate, to: LocalDate, index: Int) extends DateToken

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

final case class TimeRange(from: LocalTime, to: LocalTime, weekDay: Option[DayOfWeek], index: Int) extends DateToken

object TimeRange {
  def apply(range: ((Int, Int), (Int, Int)), weekDay: Option[DayOfWeek] = None, index: Int = 0): TimeRange = {
    val (from, to) = range
    TimeRange(
      LocalTime.of(from._1, from._2),
      LocalTime.of(to._1, to._2),
      weekDay = weekDay,
      index
    )
  }

  def apply(range: ((Int, Int), (Int, Int)), weekDay: DayOfWeek): TimeRange = {
    apply(range, Some(weekDay))
  }
}

final case class DateTimeRange(fromDate: LocalDate, toDate: Option[LocalDate], fromTime: LocalTime, toTime: Option[LocalTime], index: Int) extends DateToken

object DateTimeRange {
  def apply(dateRange: ((Int, Int, Int), Option[(Int, Int, Int)]), timeRange: ((Int, Int), Option[(Int, Int)]), index: Int = 0): DateTimeRange = {
    val (fromDate, toDate) = dateRange
    val (fromTime, toTime) = timeRange
    DateTimeRange(
      LocalDate.of(fromDate._1, fromDate._2, fromDate._3),
      toDate.map(date => LocalDate.of(date._1, date._2, date._3)),
      LocalTime.of(fromTime._1, fromTime._2),
      toTime.map(time => LocalTime.of(time._1, time._2)),
      index
    )
  }
  def apply(fromDate: (Int, Int, Int), fromTime: (Int, Int), index: Int): DateTimeRange = {
    apply((fromDate, None), (fromTime, None), index)
  }
}