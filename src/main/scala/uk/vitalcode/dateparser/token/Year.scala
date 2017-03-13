package uk.vitalcode.dateparser.token

import scala.util.Try

final case class Year(value: Int, index: Int = 0) extends TokenLike

object Year extends TokenCompanion[Year] {

  // Matches year in interval 1900-2099
  // from: http://stackoverflow.com/questions/4374185/regular-expression-match-to-test-for-a-valid-year
  private val yearRegEx =
  """(19|20)\d{2}""".r

  override def of(text: String, index: Int): Try[Year] = Try {
    yearRegEx.findFirstIn(text)
      .map(yearString => Year(yearString.toInt, index))
      .get
  }
}