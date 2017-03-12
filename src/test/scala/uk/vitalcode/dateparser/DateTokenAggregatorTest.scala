package uk.vitalcode.dateparser

import org.scalatest._
import uk.vitalcode.dateparser.token.{Date, DateRange, Day, Month, Range, Token, Year}
import uk.vitalcode.dateparser.DateTokenAggregator.{aggregate, indexTokenList}

class DateTokenAggregatorTest extends FreeSpec with ShouldMatchers {

  "extract date tokens" - {
    "[day = month = year] format" in {
      assert((Day(12) :: Month(6) :: Year(2017) :: Nil) -> (Date(2017, 6, 12, 0) :: Nil))
      assert((Day(12) :: Day(22) :: Month(6) :: Year(2017) :: Nil) -> (Day(12, 0) :: Date(2017, 6, 22, 1) :: Nil))
    }
    "[month = day = year] format" in {
      assert((Month(6) :: Day(12) :: Year(2017) :: Nil) -> (Date(2017, 6, 12, 0) :: Nil))
      assert((Month(6) :: Month(12) :: Day(12) :: Year(2017) :: Nil) -> (Month(6, 0) :: Date(2017, 12, 12, 1) :: Nil))
    }
  }

  "extract date range tokens" in {
    assert((Date(2017, 6, 12) :: Range() :: Date(2017, 6, 18) :: Nil) -> (DateRange((2017, 6, 12) -> (2017, 6, 18), 0) :: Nil))
  }

  private def assert(testExpectations: (List[Token], List[Token])) = {
    aggregate(indexTokenList(testExpectations._1)) shouldBe testExpectations._2
  }
}
