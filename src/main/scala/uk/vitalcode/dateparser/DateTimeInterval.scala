package uk.vitalcode.dateparser

import java.time.{DayOfWeek, LocalDate, LocalDateTime, LocalTime}

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

  def of(dateTokens: List[DateToken]): List[DateTimeInterval] = {
    val indexedTokens = DateTokenAggregator.indexTokenList(dateTokens)
    val aggregatedTokens = DateTokenAggregator.aggregate(indexedTokens)
    Analyser.analyse(aggregatedTokens)
  }
}
