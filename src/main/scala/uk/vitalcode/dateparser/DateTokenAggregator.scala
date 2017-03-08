package uk.vitalcode.dateparser

object DateTokenAggregator {

  def indexTokenList(tokens: List[DateToken]) = {
    tokens.zipWithIndex.map {
      case (token: Day, index) => token.copy(index = index)
      case (token: Month, index) => token.copy(index = index)
      case (token: Year, index) => token.copy(index = index)
      case (token: Date, index) => token.copy(index = index)
      case (token: Time, index) => token.copy(index = index)
      case (token: Range, index) => token.copy(index = index)
      case (token: TimeRange, index) => token.copy(index = index)
    }
  }

  def aggregate(list: List[DateToken]): List[DateToken] = {
    list match {
      case Day(d, i) :: Month(m, _) :: Year(y, _) :: tail => Date(d, m, y, i) :: aggregate(tail)
      case Month(m, i) :: Day(d, _) :: Year(y, _) :: tail => Date(d, m, y, i) :: aggregate(tail)
      case Time(from, i) :: Range(_) :: Time(to, _) :: tail => TimeRange(from, to, i) :: aggregate(tail)
      case Nil => Nil
      case _ =>  list.head :: aggregate(list.tail)
    }
  }
}
