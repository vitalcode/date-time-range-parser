package uk.vitalcode.dateparser

import org.scalatest._

class DateTimeIntervalTest extends FreeSpec with ShouldMatchers {

  "parse data time tokens list as a list of date time intervals" - {

    "single date (day, month, year tokens)" - {

      "no time tokens" in {
        assert(List(Day(6), Month(5), Year(2017)) -> List(
          DateTimeInterval.from(2017, 5, 6, 0, 0)
        ))
      }
      "single time token" in {
        assert(List(Day(6), Month(5), Year(2017), Time(12)) -> List(
          DateTimeInterval.from(2017, 5, 6, 12, 0)
        ))
      }
      "multiple time token" in {
        assert(List(Day(6), Month(5), Year(2017), Time(12), Time(14)) -> List(
          DateTimeInterval.from(2017, 5, 6, 12, 0),
          DateTimeInterval.from(2017, 5, 6, 14, 0)
        ))
      }
      "single time range" in {
        assert(List(Day(6), Month(5), Year(2017), Time(12), Range(), Time(14)) -> List(
          DateTimeInterval.from(2017, 5, 6, 12, 0).to(2017, 5, 6, 14, 0)
        ))
      }
      "multiple time range" in {
        assert(List(Day(6), Month(5), Year(2017), Time(12), Range(), Time(14), Time(18), Range(), Time(20)) -> List(
          DateTimeInterval.from(2017, 5, 6, 12, 0).to(2017, 5, 6, 14, 0),
          DateTimeInterval.from(2017, 5, 6, 18, 0).to(2017, 5, 6, 20, 0)
        ))
      }
    }

    "single date (month, day, year tokens)" in {
      assert(List(Month(9), Day(10), Year(2017), Time(12)) -> List(
        DateTimeInterval.from(2017, 9, 10, 12, 0)
      ))
    }

    "date range [month, day, year] - [day, month, year]" in {
      assert((Month(9) :: Day(10) :: Year(2017) :: Range() :: Day(12) :: Month(9) :: Year(2017) :: Nil) -> List(
        DateTimeInterval.from(2017, 9, 10, 0, 0),
        DateTimeInterval.from(2017, 9, 11, 0, 0),
        DateTimeInterval.from(2017, 9, 12, 0, 0)
      ))
    }
  }

  private def assert(testExpectations: (List[DateToken], List[DateTimeInterval])) = {
    DateTimeInterval.of(testExpectations._1) shouldBe testExpectations._2
  }
}
