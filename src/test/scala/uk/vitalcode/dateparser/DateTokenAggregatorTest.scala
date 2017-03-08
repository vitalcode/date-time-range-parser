package uk.vitalcode.dateparser

import org.scalatest._
import uk.vitalcode.dateparser.DateTokenAggregator.{aggregate, indexTokenList}

class DateTokenAggregatorTest extends FreeSpec with ShouldMatchers {

  "Extract date tokens" in {
    Seq(

      (Day(12) :: Month(6) :: Year(2017) :: Nil) -> (Date(12, 6, 2017, 0) :: Nil),
      (Day(12) :: Day(22) :: Month(6) :: Year(2017) :: Nil) -> (Day(12, 0) :: Date(22, 6, 2017, 1) :: Nil),

      (Month(6) :: Day(12) :: Year(2017) :: Nil) -> (Date(12, 6, 2017, 0) :: Nil),
      (Month(6) :: Month(12) :: Day(12) :: Year(2017) :: Nil) -> (Month(6, 0) :: Date(12, 12, 2017, 1) :: Nil)

    ).foreach(assert)
  }

  private def assert(testExpectations: (List[DateToken], List[DateToken])) = {
    aggregate(indexTokenList(testExpectations._1)) shouldBe testExpectations._2
  }
}
