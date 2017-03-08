package uk.vitalcode.dateparser

import java.time.LocalDateTime

case class DateTimeInterval(from: LocalDateTime, to: Option[LocalDateTime]) {
  def to(year: Int, month: Int, day: Int, hours: Int, minutes: Int): DateTimeInterval = copy(
    to = Some(LocalDateTime.of(year, month, day, hours, minutes, 0))
  )
}

object DateTimeInterval {
  def from(year: Int, month: Int, day: Int, hours: Int, minutes: Int): DateTimeInterval = new DateTimeInterval(
    from = LocalDateTime.of(year, month, day, hours, minutes, 0),
    to = None
  )
}

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
        to = Some(LocalDateTime.of(date.year, date.month, date.day, time.to, 0, 0))
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
