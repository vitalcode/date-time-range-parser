package uk.vitalcode.dateparser.token

import scala.util.Try

final case class Range(index: Int = 0) extends TokenLike

object Range extends TokenCompanion[Range] {

  private val rangeRegEx = """(-|to|until|\|)""".r

  override def of(token: String, index: Int): Try[Range] = Try {
    rangeRegEx.findFirstIn(token)
      .map(_ => Range(index))
      .get
  }
}
