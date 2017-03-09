package uk.vitalcode.dateparser

case object Analyser {

  def analyse(dateTokens: List[DateToken]): List[DateTimeInterval] = {
    (dates(dateTokens), times(dateTokens)) match {
      case (date :: Nil, Nil) => analyseSingleNoTimePatterns(date)
      case (date :: Nil, times: List[TimeRange]) => analyseSingleDatePatterns(date, times)
      case _ => Nil
    }
  }

  private def analyseSingleNoTimePatterns(date: Date): List[DateTimeInterval] =
    List(DateTimeInterval.from(date.year, date.month, date.day, 0, 0))

  private def analyseSingleDatePatterns(date: Date, times: List[TimeRange]): List[DateTimeInterval] = {
    times.map(time => {
      DateTimeInterval.from(date.year, date.month, date.day, time.from, 0)
          .to(date.year, date.month, date.day, time.to, 0)
    })
  }

  private def dates(dateTokens: List[DateToken]) = dateTokens.collect {
    case d: Date => d
  }

  private def times(dateTokens: List[DateToken]) = dateTokens.collect {
    case t: TimeRange => t
  }
}
