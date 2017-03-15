package uk.vitalcode.dateparser.token

import java.text.DateFormatSymbols
import java.util.Locale

import scala.util.Try

final case class Month(value: Int, index: Int = 0) extends TokenLike

object Month extends TokenCompanion[Month] {

  private val months: Seq[String] = new DateFormatSymbols(Locale.UK).getMonths.map(m => m.toLowerCase())

  private val shortMonths: Seq[String] = new DateFormatSymbols(Locale.UK).getShortMonths.filter(m => m.nonEmpty).map(m => m.toLowerCase())

  def of(token: String, index: Int): Try[Month] = Try {
    val tokenLowCase = token.toLowerCase()
    val monthsIndex = months.indexOf(tokenLowCase)
    val shortMonthsIndex = shortMonths.find(m => tokenLowCase.contains(m)).fold(-1)(shortMonths.indexOf)

    if (monthsIndex != -1) Month(monthsIndex + 1, index)
    else if (shortMonthsIndex != -1) Month(shortMonthsIndex + 1, index)
    else throw new Exception(s"Error while parsing [$token] as a Month token")
  }
}
