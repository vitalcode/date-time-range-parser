package uk.vitalcode.dateparser

import org.scalatest._
import uk.vitalcode.dateparser.DateTokenAggregator.{aggregate, indexTokenList}

class DateTokenAggregatorTest extends FreeSpec with ShouldMatchers {

  "extract date tokens" - {
    "[day = month = year] format" in {
      assert((Day(12) :: Month(6) :: Year(2017) :: Nil) -> (Date(12, 6, 2017, 0) :: Nil))
      assert((Day(12) :: Day(22) :: Month(6) :: Year(2017) :: Nil) -> (Day(12, 0) :: Date(22, 6, 2017, 1) :: Nil))
    }
    "[month = day = year] format" in {
      assert((Month(6) :: Day(12) :: Year(2017) :: Nil) -> (Date(12, 6, 2017, 0) :: Nil))
      assert((Month(6) :: Month(12) :: Day(12) :: Year(2017) :: Nil) -> (Month(6, 0) :: Date(12, 12, 2017, 1) :: Nil))
    }
  }

  "extract date range tokens" in {
    assert((Date(12, 6, 2017) :: Range() :: Date(18, 6, 2017) :: Nil) -> (DateRange(Date(12, 6, 2017), Date(18, 6, 2017), 0) :: Nil))
  }

  private def assert(testExpectations: (List[DateToken], List[DateToken])) = {
    aggregate(indexTokenList(testExpectations._1)) shouldBe testExpectations._2
  }
}
