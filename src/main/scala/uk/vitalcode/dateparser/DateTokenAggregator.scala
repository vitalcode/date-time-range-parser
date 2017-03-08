package uk.vitalcode.dateparser

object DateTokenAggregator {

  def aggregate(list: List[DateToken]): List[DateToken] = {
    list match {
      case Day(d, i) :: Month(m, _) :: Year(y, _) :: tail => Date(d, m, y, i) :: aggregate(tail)
      case Time(from, i) :: Range(_) :: Time(to, _) :: tail => TimeRange(from, to, i) :: aggregate(tail)
      case Nil => Nil
      case _ =>  list.head :: aggregate(list.tail)
    }
  }
}
