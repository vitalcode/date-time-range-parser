package uk.vitalcode.dateparser

case object Analyser {

  def analyse(tokens: List[DateToken]): List[DateTimeInterval] = {
    (dates(tokens), times(tokens), timeRanges(tokens)) match {
      case (date :: Nil, Nil, Nil) => analyseSingleNoTimePatterns(date)
      case (date :: Nil, times: List[Time], Nil) => analyseSingleDateTimePatterns(date, times)
      case (date :: Nil, _, timeRanges: List[TimeRange]) => analyseSingleDateTimeRangesPatterns(date, timeRanges)
      case _ => Nil
    }
  }

  private def analyseSingleNoTimePatterns(date: Date): List[DateTimeInterval] =
    List(DateTimeInterval.from(date.year, date.month, date.day, 0, 0))

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

  private def times(dateTokens: List[DateToken]) = dateTokens.collect {
    case t: Time => t
  }

  private def timeRanges(dateTokens: List[DateToken]) = dateTokens.collect {
    case t: TimeRange => t
  }
}
