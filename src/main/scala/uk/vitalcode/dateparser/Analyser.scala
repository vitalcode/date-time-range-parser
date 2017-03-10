package uk.vitalcode.dateparser

import java.time.{DayOfWeek, LocalDate, LocalDateTime, LocalTime}

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
    List(DateTimeInterval.from(date.year, date.month, date.day, 0, 0))

  private def analyseDateRangeNoTimePatterns(dateRange: DateRange, weekDays: Set[DayOfWeek]): List[DateTimeInterval] = {
    val from = dateRange.from
    val to = dateRange.to
    val fromDate = LocalDate.of(from.year, from.month, from.day)
    val toDate = LocalDate.of(to.year, to.month, to.day)

    DateTimeUtils.datesInRange(fromDate, toDate, weekDays)
      .map(localDate => DateTimeInterval(LocalDateTime.of(localDate, LocalTime.of(0, 0)), None))
  }

  private def analyseDateRangeTimePatterns(dateRange: DateRange, weekDays: Set[DayOfWeek], times: List[Time]): List[DateTimeInterval] = {
    val from = dateRange.from
    val to = dateRange.to
    val fromDate = LocalDate.of(from.year, from.month, from.day)
    val toDate = LocalDate.of(to.year, to.month, to.day)

    for {
      localDate <- DateTimeUtils.datesInRange(fromDate, toDate, weekDays)
      time <- times
    } yield DateTimeInterval(LocalDateTime.of(localDate, LocalTime.of(time.value, 0)), None)
  }

  private def analyseDateRangeTimeTimeRangesPatterns(dateRange: DateRange, weekDays: Set[DayOfWeek], timeRanges: List[TimeRange]) = {
    val from = dateRange.from
    val to = dateRange.to
    val fromDate = LocalDate.of(from.year, from.month, from.day)
    val toDate = LocalDate.of(to.year, to.month, to.day)

    for {
      localDate <- DateTimeUtils.datesInRange(fromDate, toDate, weekDays)
      timeRange <- timeRanges
    } yield DateTimeInterval(
      LocalDateTime.of(localDate, LocalTime.of(timeRange.from, 0)),
      Some(LocalDateTime.of(localDate, LocalTime.of(timeRange.to, 0)))
    )
  }

  private def analyseSingleDateTimeRangesPatterns(date: Date, timeRanges: List[TimeRange]): List[DateTimeInterval] = {
    timeRanges.map(time => {
      DateTimeInterval.from(date.year, date.month, date.day, time.from, 0)
        .to(date.year, date.month, date.day, time.to, 0)
    })
  }

  private def analyseSingleDateTimePatterns(date: Date, times: List[Time]): List[DateTimeInterval] = {
    times.map(time => {
      DateTimeInterval.from(date.year, date.month, date.day, time.value, 0)
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
