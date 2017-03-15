package uk.vitalcode.dateparser.token

import java.text.DateFormatSymbols
import java.time.DayOfWeek
import java.util.Locale

import scala.util.Try

final case class WeekDay(value: DayOfWeek, index: Int = 0) extends TokenLike

object WeekDay extends TokenCompanion[WeekDay] {

  private val weekdays: Seq[String] = new DateFormatSymbols(Locale.UK).getWeekdays.map(w => w.toLowerCase)

  private val shortWeekdays: Seq[String] = new DateFormatSymbols(Locale.UK).getShortWeekdays.map(w => w.toLowerCase)

  private def getDayOfWeek(index: Int): DayOfWeek = DayOfWeek.of(if (index > 1) index - 1 else 7)

  override def of(token: String, index: Int): Try[WeekDay] = Try {
    val tokenLowCase = token.toLowerCase
    val weekdaysIndex = weekdays.indexOf(tokenLowCase)
    val shortWeekdaysIndex = shortWeekdays.indexOf(tokenLowCase)

    if (weekdaysIndex != -1) WeekDay(getDayOfWeek(weekdaysIndex), index)
    else if (shortWeekdaysIndex != -1) WeekDay(getDayOfWeek(shortWeekdaysIndex), index)
    else throw new Exception(s"Error while parsing [$token] as a WeekDay token")
  }
}
