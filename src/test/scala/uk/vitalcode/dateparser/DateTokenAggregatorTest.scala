package uk.vitalcode.dateparser

import org.scalatest._
import uk.vitalcode.dateparser.token.{Date, DateRange, Day, Month, Range, DateToken, Year}
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
      assert((Month(6) :: Day(12) :: Nil) -> (Date(2017, 6, 12, 0).get :: Nil))
    }
  }

  "extract date range tokens" in {
    assert((Date(2017, 6, 12).get :: Range() :: Date(2017, 6, 18).get :: Nil) -> (DateRange((2017, 6, 12) -> (2017, 6, 18), 0) :: Nil))
  }

  private def assert(testExpectations: (List[DateToken], List[DateToken])) = {
    aggregate(indexTokenList(testExpectations._1), new DefaultDateTimeProvider) shouldBe testExpectations._2
  }
}
