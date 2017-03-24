package uk.vitalcode.dateparser

import java.time.{DayOfWeek, LocalDate, LocalDateTime, LocalTime}

import uk.vitalcode.dateparser.DateTimeInterval.defaultTime
import uk.vitalcode.dateparser.token._

case object Analyser {

  def analyse(tokens: List[DateToken]): List[DateTimeInterval] = {
    val dateTokens = dates(tokens)
    val dateRangeTokens = dateRanges(tokens)
    val weekDayTokens = weekDays(tokens)
    val timeTokens = times(tokens)
    val timeRangeTokens = timeRanges(tokens)
    val dateTimeRangeTokens = dateTimeRange(tokens)

    (dateTokens, dateRangeTokens, weekDayTokens, timeTokens, timeRangeTokens, dateTimeRangeTokens) match {
      case (_ , dateRange :: Nil, weekDays, Nil, Nil,_) =>
        analyseDateRangeNoTimePatterns(dateRange, weekDays)
      case (_ , dateRange :: Nil, weekDays, times, Nil,_) =>
        analyseDateRangeTimePatterns(dateRange, weekDays, times)
      case (_ , dateRange :: Nil, weekDays, _, timeRanges: List[TimeRange], _) =>
        analyseDateRangeTimeTimeRangesPatterns(dateRange, weekDays, timeRanges)
      case (date :: Nil, _, _, Nil, Nil, _) =>
        analyseSingleNoTimePatterns(date)
      case (date :: Nil, _, _, times: List[Time], Nil, _) =>
        analyseSingleDateTimePatterns(date, times)
      case (date :: Nil, _, _, _, timeRanges: List[TimeRange], _) =>
        analyseSingleDateTimeRangesPatterns(date, timeRanges)
      case (_, _, _, Nil, _, dateTimeRanges) =>
        analyseDateTimeRangesNoTimePatterns(dateTimeRanges)
      case (_, _, _, times, _, dateTimeRange :: Nil) =>
        analyseDateTimeRangeTimesPatterns(dateTimeRange, times)
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
      localDate <- DateTimeUtils.datesInRange(dateRange.from, dateRange.to, weekDays ++ timeRanges.collect {
        case TimeRange(_,_, Some(weekDay),_) => weekDay
      })
      timeRange <- timeRanges if timeRange.weekDay.isEmpty || timeRange.weekDay.get == localDate.getDayOfWeek
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

  private def analyseDateTimeRangesNoTimePatterns(dateTimeRanges: List[DateTimeRange]): List[DateTimeInterval] = {
    dateTimeRanges.map {
      case DateTimeRange(fromDate, None, fromTime, _, _) => DateTimeInterval.from(fromDate, fromTime)
      case DateTimeRange(fromDate, Some(toDate), fromTime, None, _) => DateTimeInterval.from(fromDate, fromTime).to(toDate, LocalTime.of(0, 0))
      case DateTimeRange(fromDate, Some(toDate), fromTime, Some(toTime), _) => DateTimeInterval.from(fromDate, fromTime).to(toDate, toTime)
    }
  }

  private def analyseDateTimeRangeTimesPatterns(dateTimeRange: DateTimeRange, times: List[Time]): List[DateTimeInterval] = {
    val fromDate = dateTimeRange.fromDate
    val toDate = dateTimeRange.toDate
    val toTime = None

    val dateTimeRanges =  dateTimeRange +: times.map(time => DateTimeRange(fromDate, toDate, time.value, None, time.index))
    analyseDateTimeRangesNoTimePatterns(dateTimeRanges)
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

  private def dateTimeRange(dateTokens: List[DateToken]) = dateTokens.collect {
    case t: DateTimeRange => t
  }
}
