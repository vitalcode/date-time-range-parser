package uk.vitalcode.dateparser

import java.time.{LocalDate, LocalDateTime, LocalTime}

import uk.vitalcode.dateparser.token.DateToken

case class DateTimeInterval(from: LocalDateTime, to: Option[LocalDateTime]) {
  def to(date: LocalDate, time: LocalTime): DateTimeInterval = copy(
    to = Some(LocalDateTime.of(date, time))
  )

  def to(year: Int, month: Int, day: Int, hours: Int = 0, minutes: Int = 0): DateTimeInterval = copy(
    to = Some(LocalDateTime.of(year, month, day, hours, minutes))
  )
}

object DateTimeInterval {

  val defaultTime = LocalTime.of(0, 0)

  def from(date: LocalDate, time: LocalTime): DateTimeInterval = new DateTimeInterval(
    from = LocalDateTime.of(date, time),
    to = None
  )

  def from(year: Int, month: Int, day: Int, hours: Int = 0, minutes: Int = 0): DateTimeInterval = new DateTimeInterval(
    from = LocalDateTime.of(year, month, day, hours, minutes),
    to = None
  )

  def of(dateTokens: List[DateToken], tp: DateTimeProvider): List[DateTimeInterval] = {
    val aggregatedTokens = aggregateTokens(dateTokens, tp)
    Analyser.analyse(aggregatedTokens)
  }

  def of(text: String, tp: DateTimeProvider = new DefaultDateTimeProvider): List[DateTimeInterval] = {
    val tokens = DateToken.parse(text)
    of(tokens, tp)
  }

  private def aggregateTokens(dateTokens: List[DateToken], timeProvider: DateTimeProvider): List[DateToken] = {
    val aggregatedTokens = DateTokenAggregator.aggregate(dateTokens, timeProvider)
    if (aggregatedTokens == dateTokens) aggregatedTokens
    else aggregateTokens(aggregatedTokens, timeProvider)
  }
}
