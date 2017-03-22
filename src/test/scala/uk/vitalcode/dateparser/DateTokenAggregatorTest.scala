package uk.vitalcode.dateparser

import java.time.{DayOfWeek, LocalDateTime}

import org.scalatest._
import uk.vitalcode.dateparser.token._
import uk.vitalcode.dateparser.DateTokenAggregator.{aggregate, indexTokenList}

class DateTokenAggregatorTest extends FreeSpec with Matchers {

  "extract date tokens" - {
    "[day = month = year] format" in {
      assert((Day(12) :: Month(6) :: Year(2017) :: Nil) -> (Date(2017, 6, 12, 0).get :: Nil))
      assert((Day(12) :: Day(22) :: Month(6) :: Year(2017) :: Nil) -> (Day(12, 0) :: Date(2017, 6, 22, 1).get :: Nil))
    }
    "[month = day = year] format" in {
      assert((Month(6) :: Day(12) :: Year(2017) :: Nil) -> (Date(2017, 6, 12, 0).get :: Nil))
      assert((Month(6) :: Month(12) :: Day(12) :: Year(2017) :: Nil) -> (Month(6, 0) :: Date(2017, 12, 12, 1).get :: Nil))
    }
    "[day = month] format" in {
      assert((Day(12) :: Month(6) :: Nil) -> (Date(2017, 6, 12, 0).get :: Nil))
    }
    "[month = day] format" in {
      assert((Month(3) :: Day(25) :: Nil) -> (Date(2018, 3, 25, 0).get :: Nil))
    }
  }

  "extract date range tokens" in {
    assert((Date(2017, 6, 12).get :: Range() :: Date(2017, 6, 18).get :: Nil) -> (DateRange((2017, 6, 12) -> (2017, 6, 18), 0) :: Nil))
  }

  "extract time range tokens" - {
    "range token between time tokens" - {
      "no day of the week defined" in {
        assert((Time(19, 30) :: Range() :: Time(20, 45) :: Nil) -> (TimeRange((19, 30) -> (20, 45), None, 0) :: Nil))
      }
      "with day of the week defined" in {
        assert((WeekDay(DayOfWeek.SUNDAY) :: Time(19, 30) :: Range() :: Time(20, 45) :: Nil) -> (TimeRange((19, 30) -> (20, 45), Some(DayOfWeek.SUNDAY), 0) :: Nil))
      }
    }
    "no range token" - {
      "time tokens are adjacent in original text" - {
        "no day of the week defined" in {
          assertNoIndex((Time(19, 30, 0, 0) :: Time(20, 45, 0, 1) :: Nil) -> (TimeRange((19, 30) -> (20, 45), None, 0) :: Nil))
        }
        "with day of the week defined" in {
          assertNoIndex((WeekDay(DayOfWeek.SUNDAY, 0) :: Time(19, 30, 0, 1) :: Time(20, 45, 0, 2) :: Nil) -> (TimeRange((19, 30) -> (20, 45), Some(DayOfWeek.SUNDAY), 0) :: Nil))
        }
      }
      "separators between time tokens in original text" - {
        "no day of the week defined" in {
          assertNoIndex((Time(19, 30, 0, 0) :: Time(20, 45, 0, 2) :: Nil) -> (Time(19, 30, 0, 0) :: Time(20, 45, 0, 2) :: Nil))
        }
        "with day of the week defined" in {
          assertNoIndex((WeekDay(DayOfWeek.SUNDAY, 0) :: Time(19, 30, 0, 1) :: Time(20, 45, 0, 3) :: Nil) -> (WeekDay(DayOfWeek.SUNDAY, 0) :: Time(19, 30, 0, 1) :: Time(20, 45, 0, 3) :: Nil))
        }
      }
    }
  }

  private val dateTimeProvider = new DateTimeProvider {
    override def now: LocalDateTime = LocalDateTime.of(2017, 5, 6, 0, 0)
  }

  private def assert(testExpectations: (List[DateToken], List[DateToken])) = {
    aggregate(indexTokenList(testExpectations._1), dateTimeProvider) shouldBe testExpectations._2
  }

  private def assertNoIndex(testExpectations: (List[DateToken], List[DateToken])) = {
    aggregate(testExpectations._1, dateTimeProvider) shouldBe testExpectations._2
  }
}
