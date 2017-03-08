package uk.vitalcode.dateparser

import org.scalatest._
import uk.vitalcode.dateparser.DateTokenAggregator.aggregate

class DateTokenAggregatorTest extends FreeSpec with ShouldMatchers {

  "Extract date tokens" in {
    aggregate(Day(12, 0) :: Month(6, 1) :: Year(2017, 2) :: Nil) shouldBe Date(12,6,2017,0) :: Nil
  }
}
