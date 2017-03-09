package uk.vitalcode.dateparser

import org.scalatest._

class DateTimeIntervalTest extends FreeSpec with ShouldMatchers {

  "Parse data time tokens list as a list of date time intervals" in {
    Seq(
      (Day(6) :: Month(5) :: Year(2017) :: Nil) -> (DateTimeInterval.from(2017, 5, 6, 0, 0) :: Nil)
    ).foreach(assert)
  }

  private def assert(testExpectations: (List[DateToken], List[DateTimeInterval])) = {
    DateTimeInterval.of(testExpectations._1) shouldBe testExpectations._2
  }
}
