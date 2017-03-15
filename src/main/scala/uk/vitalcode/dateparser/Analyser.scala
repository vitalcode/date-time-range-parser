package uk.vitalcode.dateparser

import java.time.{DayOfWeek, LocalDate, LocalDateTime, LocalTime}

import uk.vitalcode.dateparser.DateTimeInterval.defaultTime
import uk.vitalcode.dateparser.token._

case object Analyser {

  def analyse(tokens: List[DateToken]): List[DateTimeInterval] = {
    (dates(tokens), dateRanges(tokens), weekDays(tokens), times(tokens), timeRanges(tokens)) match {
      case (_ , dateRange :: Nil, weekDays, Nil, Nil) => analyseDateRangeNoTimePatterns(dateRange, weekDays)
      case (_ , dateRange :: Nil, weekDays, times, Nil) => analyseDateRangeTimePatterns(dateRange, weekDays, times)
      case (_ , dateRange :: Nil, weekDays, _, timeRanges: List[TimeRange]) => analyseDateRangeTimeTimeRangesPatterns(dateRange, weekDays, timeRanges)
      case (date :: Nil, _, _, Nil, Nil) => analyseSingleNoTimePatterns(date)
      case (date :: Nil, _, _, times: List[Time], Nil) => analyseSingleDateTimePatterns(date, times)
      case (date :: Nil, _, _, _, timeRanges: List[TimeRange]) => analyseSingleDateTimeRangesPatterns(date, timeRanges)
      case _ => Nil
    }
  }

  private def analyseSingleNoTimePatterns(date: Date): List[DateTimeInterval] =
    List(DateTimeInterval.from(date.value, defaultTime))

  private def analyseDateRangeNoTimePatterns(dateRange: DateRange, weekDays: Set[DayOfWeek]): List[DateTimeInterval] = {
    DateTimeUtils.datesInRange(dateRange.from, dateRange.to, weekDays)
      .map(localDate => DateTimeInterval(LocalDateTime.of(localDate, LocalTime.of(0, 0)), None))
  }

  private def analyseDateRangeTimePatterns(dateRange: DateRange, weekDays: Set[DayOfWeek], times: List[Time]): List[DateTimeInterval] = {
    for {
      localDate <- DateTimeUtils.datesInRange(dateRange.from, dateRange.to, weekDays)
      time <- times
    } yield DateTimeInterval(LocalDateTime.of(localDate, time.value), None)
  }

  private def analyseDateRangeTimeTimeRangesPatterns(dateRange: DateRange, weekDays: Set[DayOfWeek], timeRanges: List[TimeRange]) = {
    for {
      localDate <- DateTimeUtils.datesInRange(dateRange.from, dateRange.to, weekDays)
      timeRange <- timeRanges
    } yield DateTimeInterval(
      LocalDateTime.of(localDate, timeRange.from),
      Some(LocalDateTime.of(localDate, timeRange.to))
    )
  }

  private def analyseSingleDateTimeRangesPatterns(date: Date, timeRanges: List[TimeRange]): List[DateTimeInterval] = {
    timeRanges.map(time => {
      DateTimeInterval.from(date.value, time.from)
        .to(date.value, time.to)
    })
  }

  private def analyseSingleDateTimePatterns(date: Date, times: List[Time]): List[DateTimeInterval] = {
    times.map(time => {
      DateTimeInterval.from(date.value, time.value)
    })
  }

  private def dates(dateTokens: List[DateToken]) = dateTokens.collect {
    case d: Date => d
  }

  private def dateRanges(dateTokens: List[DateToken]) = dateTokens.collect {
    case t: DateRange => t
  }

  private def times(dateTokens: List[DateToken]) = dateTokens.collect {
    case t: Time => t
  }

  private def timeRanges(dateTokens: List[DateToken]) = dateTokens.collect {
    case t: TimeRange => t
  }

  private def weekDays(dateTokens: List[DateToken]) = dateTokens.collect {
    case t: WeekDay => t.value
  }.toSet
}
