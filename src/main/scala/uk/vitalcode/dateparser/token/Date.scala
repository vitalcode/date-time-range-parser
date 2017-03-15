package uk.vitalcode.dateparser.token

import java.time.LocalDate

final case class Date(value: LocalDate, index: Int) extends TokenLike

object Date {

  def apply(year: Int, month: Int, day: Int, index: Int = 0): Date = {
    Date(LocalDate.of(year, month, day), index)
  }
}

