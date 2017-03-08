package uk.vitalcode.dateparser

import java.time.LocalDateTime

case class DateTimeInterval(from: LocalDateTime, to: LocalDateTime)

case object Analyser {

  def analyse(dateTokens: List[DateToken]): List[DateTimeInterval] = {
    (dates(dateTokens), times(dateTokens)) match {
      case (date :: Nil, times: List[TimeRange]) => analyseSingleDatePatterns(date, times)
      case _ => Nil
    }
  }

  private def analyseSingleDatePatterns(date: Date, times: List[TimeRange]): List[DateTimeInterval] = {
    times.map(time => {
      DateTimeInterval(
        from = LocalDateTime.of(date.year, date.month, date.day, time.from, 0, 0),
        to = LocalDateTime.of(date.year, date.month, date.day, time.to, 0, 0)
      )
    })
  }

  private def dates(dateTokens: List[DateToken]) = dateTokens.collect {
    case d: Date => d
  }

  private def times(dateTokens: List[DateToken]) = dateTokens.collect {
    case t: TimeRange => t
  }
}
