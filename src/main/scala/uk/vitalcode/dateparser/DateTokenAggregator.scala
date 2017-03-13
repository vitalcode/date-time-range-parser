package uk.vitalcode.dateparser

import uk.vitalcode.dateparser.token.{Date, DateRange, Day, Month, Range, TimeRange, Time, TokenLike, WeekDay, Year}

object DateTokenAggregator {

  def indexTokenList(tokens: List[TokenLike]) = {
    tokens.zipWithIndex.map {
      case (token: Day, index) => token.copy(index = index)
      case (token: Month, index) => token.copy(index = index)
      case (token: Year, index) => token.copy(index = index)
      case (token: Date, index) => token.copy(index = index)
      case (token: DateRange, index) => token.copy(index = index)
      case (token: Time, index) => token.copy(index = index)
      case (token: Range, index) => token.copy(index = index)
      case (token: TimeRange, index) => token.copy(index = index)
      case (token: WeekDay, index) => token.copy(index = index)
    }
  }

  def aggregate(list: List[TokenLike]): List[TokenLike] = {
    list match {
      case Day(d, i) :: Month(m, _) :: Year(y, _) :: tail => Date(y, m, d, i) :: aggregate(tail)
      case Month(m, i) :: Day(d, _) :: Year(y, _) :: tail => Date(y, m, d, i) :: aggregate(tail)
      case Time(from, i) :: Range(_) :: Time(to, _) :: tail => TimeRange(from, to, i) :: aggregate(tail)
      case Date(from, i) :: Range(_) :: Date(to, _) :: tail => DateRange(from, to, i) :: aggregate(tail)
      case Nil => Nil
      case _ =>  list.head :: aggregate(list.tail)
    }
  }
}
