package uk.vitalcode.dateparser.token

import java.time.LocalTime
import java.time.format.DateTimeFormatter

import scala.util.Try

final case class Time(value: LocalTime, index: Int = 0) extends TokenLike

object Time extends TokenCompanion[Time] {

  def apply(hours: Int, minutes: Int): Time = Time(LocalTime.of(hours, minutes))

  def apply(hours: Int, minutes: Int, seconds: Int): Time = Time(LocalTime.of(hours, minutes, seconds))

  private val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern(""
    + "[h.m[.s]a]"
    + "[h[:m][:s]a]"
    + "[H.m[.s]]"
    + "[H:m[:s]]"
  )

  // matches with either : or .  separators,
  // will match a 24 hour time, or a 12 hour time with AM or PM specified,
  // allows 0-59 minutes, and 0-59 seconds. Seconds are not required.
  // http://regexlib.com/REDetails.aspx?regexp_id=144
  private val timeRegEx =
  """((([0]?[1-9]|1[0-2])((:|\.)[0-5]?[0-9])?((:|\.)[0-5]?[0-9])?( )?(AM|am|aM|Am|PM|pm|pM|Pm))|(([0]?[0-9]|1[0-9]|2[0-3])(:|\.)[0-5]?[0-9]((:|\.)[0-5]?[0-9])?))""".r

  override def of(text: String, index: Int): Try[Time] = Try {
    timeRegEx.findFirstIn(text)
      .map(_.replaceAll("""\s*""", "").toUpperCase)
      .map(timeString => LocalTime.parse(timeString, timeFormatter))
      .map(localTime => Time(localTime, index))
      .get
  }
}