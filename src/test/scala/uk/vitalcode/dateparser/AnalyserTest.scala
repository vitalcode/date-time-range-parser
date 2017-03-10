package uk.vitalcode.dateparser

import java.time.DayOfWeek

import org.scalatest._
import uk.vitalcode.dateparser.Analyser.analyse
import uk.vitalcode.dateparser.DateTokenAggregator.indexTokenList

class AnalyserTest extends FreeSpec with ShouldMatchers {

  "Single date token" - {
    "no either time nor time range token" in {
      assert(List(Date(12, 2, 2017)) -> List(
        DateTimeInterval.from(2017, 2, 12, 0, 0)
      ))
    }
    "single time token" in {
      assert(List(Date(12, 2, 2017), Time(10)) -> List(
        DateTimeInterval.from(2017, 2, 12, 10, 0)
      ))
    }
    "multiple time tokens" in {
      assert(List(Date(12, 2, 2017), Time(10), Time(14)) -> List(
        DateTimeInterval.from(2017, 2, 12, 10, 0),
        DateTimeInterval.from(2017, 2, 12, 14, 0)
      ))
    }
    "single time range token" in {
      assert(List(Date(12, 2, 2017), TimeRange(10, 11)) -> List(
        DateTimeInterval.from(2017, 2, 12, 10, 0).to(2017, 2, 12, 11, 0)
      ))
    }
    "multiple time range token" in {
      assert(List(Date(12, 2, 2017), TimeRange(10, 11), TimeRange(14, 15)) -> List(
        DateTimeInterval.from(2017, 2, 12, 10, 0).to(2017, 2, 12, 11, 0),
        DateTimeInterval.from(2017, 2, 12, 14, 0).to(2017, 2, 12, 15, 0)
      ))
    }
  }

  "Date range token" - {
    "no weekday filter" - {
      "no either time nor time range token" in {
        assert(
          (DateRange(Date(12, 2, 2017), Date(14, 2, 2017)) :: Nil) -> List(
            DateTimeInterval.from(2017, 2, 12, 0, 0),
            DateTimeInterval.from(2017, 2, 13, 0, 0),
            DateTimeInterval.from(2017, 2, 14, 0, 0)
          )
        )
      }
      "single time token" in {
        assert((DateRange(Date(12, 2, 2017), Date(14, 2, 2017)) :: Time(10) :: Nil) -> List(
          DateTimeInterval.from(2017, 2, 12, 10, 0),
          DateTimeInterval.from(2017, 2, 13, 10, 0),
          DateTimeInterval.from(2017, 2, 14, 10, 0)
        ))
      }
      "multiple time tokens" in {
        assert((DateRange(Date(12, 2, 2017), Date(14, 2, 2017)) :: Time(10) :: Time(14) :: Nil) -> List(
          DateTimeInterval.from(2017, 2, 12, 10, 0),
          DateTimeInterval.from(2017, 2, 12, 14, 0),
          DateTimeInterval.from(2017, 2, 13, 10, 0),
          DateTimeInterval.from(2017, 2, 13, 14, 0),
          DateTimeInterval.from(2017, 2, 14, 10, 0),
          DateTimeInterval.from(2017, 2, 14, 14, 0)
        ))
      }
      "single time range token" in {
        assert((DateRange(Date(12, 2, 2017), Date(14, 2, 2017)) :: TimeRange(10, 11) :: Nil) -> List(
          DateTimeInterval.from(2017, 2, 12, 10, 0).to(2017, 2, 12, 11, 0),
          DateTimeInterval.from(2017, 2, 13, 10, 0).to(2017, 2, 13, 11, 0),
          DateTimeInterval.from(2017, 2, 14, 10, 0).to(2017, 2, 14, 11, 0)
        ))
      }
      "multiple time range token" in {
        assert((DateRange(Date(12, 2, 2017), Date(14, 2, 2017)) :: TimeRange(10, 11) :: TimeRange(14, 15) :: Nil) -> List(
          DateTimeInterval.from(2017, 2, 12, 10, 0).to(2017, 2, 12, 11, 0),
          DateTimeInterval.from(2017, 2, 12, 14, 0).to(2017, 2, 12, 15, 0),
          DateTimeInterval.from(2017, 2, 13, 10, 0).to(2017, 2, 13, 11, 0),
          DateTimeInterval.from(2017, 2, 13, 14, 0).to(2017, 2, 13, 15, 0),
          DateTimeInterval.from(2017, 2, 14, 10, 0).to(2017, 2, 14, 11, 0),
          DateTimeInterval.from(2017, 2, 14, 14, 0).to(2017, 2, 14, 15, 0)
        ))
      }
    }
    "with weekday filter" - {
      "no either time nor time range token" in {
        assert(
          (DateRange(Date(12, 2, 2017), Date(28, 2, 2017)) :: WeekDay(DayOfWeek.TUESDAY) :: WeekDay(DayOfWeek.FRIDAY) :: Nil) -> List(
            DateTimeInterval.from(2017, 2, 14, 0, 0),
            DateTimeInterval.from(2017, 2, 17, 0, 0),
            DateTimeInterval.from(2017, 2, 21, 0, 0),
            DateTimeInterval.from(2017, 2, 24, 0, 0),
            DateTimeInterval.from(2017, 2, 28, 0, 0)
          )
        )
      }
    }
  }

  "Date range token with " - {
    "no either time nor time range token" in {
      assert(
        (DateRange(Date(12, 2, 2017), Date(14, 2, 2017)) :: Nil) -> List(
          DateTimeInterval.from(2017, 2, 12, 0, 0),
          DateTimeInterval.from(2017, 2, 13, 0, 0),
          DateTimeInterval.from(2017, 2, 14, 0, 0)
        )
      )
    }
  }

  private def assert(testExpectations: (List[DateToken], List[DateTimeInterval])) = {
    analyse(indexTokenList(testExpectations._1)) shouldBe testExpectations._2
  }
}
