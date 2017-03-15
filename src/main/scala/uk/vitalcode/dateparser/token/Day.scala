package uk.vitalcode.dateparser.token

import scala.util.Try

final case class Day(value: Int, index: Int = 0) extends DateToken

object Day extends TokenCompanion[Day] {

  private val dayOfMonthRegEx = """[0123]?[0-9]""".r

  override def of(token: String, index: Int): Try[Day] = Try {
    dayOfMonthRegEx.findFirstIn(token)
      .map(dayString => Day(dayString.toInt, index))
      .get
  }
}
