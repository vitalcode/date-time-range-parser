package uk.vitalcode.dateparser

import java.time.LocalDateTime

import org.scalatest._
import uk.vitalcode.dateparser.token.{Date, DateRange, DateToken, Day, Month, Range, Year}
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

  private val dateTimeProvider = new DateTimeProvider {
    override def now: LocalDateTime = LocalDateTime.of(2017, 5, 6, 0, 0)
  }

  private def assert(testExpectations: (List[DateToken], List[DateToken])) = {
    aggregate(indexTokenList(testExpectations._1), dateTimeProvider) shouldBe testExpectations._2
  }
}
