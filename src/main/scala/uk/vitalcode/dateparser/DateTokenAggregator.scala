package uk.vitalcode.dateparser

import uk.vitalcode.dateparser.DateTimeUtils.getYearForNextMonthAndDay
import uk.vitalcode.dateparser.token.{Date, DateRange, DateTimeRange, DateToken, Day, Month, Range, Time, TimeRange, WeekDay, Year}

object DateTokenAggregator {

  def indexTokenList(tokens: List[DateToken]) = {
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

  def aggregate(list: List[DateToken], tp: DateTimeProvider): List[DateToken] = {
    list match {
      case Day(d, i) :: Month(m, _) :: Year(y, _) :: tail if Date(y, m, d, i).isSuccess => Date(y, m, d, i).get :: aggregate(tail, tp)
      case Month(m, i) :: Day(d, _) :: Year(y, _) :: tail if Date(y, m, d, i).isSuccess => Date(y, m, d, i).get :: aggregate(tail, tp)

      case Day(d, i) :: Month(m, _) :: tail if Date(getYearForNextMonthAndDay(m, d, tp), m, d, i).isSuccess => Date(getYearForNextMonthAndDay(m, d, tp), m, d, i).get :: aggregate(tail, tp)
      case Month(m, i) :: Day(d, _) :: tail if Date(getYearForNextMonthAndDay(m, d, tp), m, d, i).isSuccess => Date(getYearForNextMonthAndDay(m, d, tp), m, d, i).get :: aggregate(tail, tp)

      case Time(from, i) :: Range(_) :: Time(to, _) :: tail => TimeRange(from, to, None, i) :: aggregate(tail, tp)
      case Time(from, fi) :: Time(to, ti) :: tail if ti - fi == 1 => TimeRange(from, to, None, fi) :: aggregate(tail, tp)
      case WeekDay(wd, i) :: Time(from, _) :: Range(_) :: Time(to, _) :: tail => TimeRange(from, to, Some(wd), i) :: aggregate(tail, tp)
      case WeekDay(wd, i) :: Time(from, fi) :: Time(to, ti) :: tail if ti - fi == 1=> TimeRange(from, to, Some(wd), i) :: aggregate(tail, tp)

      case Date(from, i) :: Range(_) :: Date(to, _) :: tail => DateRange(from, to, i) :: aggregate(tail, tp)

      case Date(fd, i) :: Time(ft, _) :: tail => DateTimeRange(fd, None, ft, None, i) :: aggregate(tail, tp)

      case Nil => Nil
      case _ =>  list.head :: aggregate(list.tail, tp)
    }
  }
}
