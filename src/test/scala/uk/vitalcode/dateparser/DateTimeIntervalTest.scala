package uk.vitalcode.dateparser

import org.scalatest._

class DateTimeIntervalTest extends FreeSpec with ShouldMatchers {

  "Parse data time tokens list as a list of date time intervals" in {
    Seq(
      (Day(6) :: Month(5) :: Year(2017) :: Nil) -> (DateTimeInterval.from(2017, 5, 6, 0, 0) :: Nil),
      (Month(9) :: Day(10) :: Year(2017) :: Time(12) :: Nil) -> (DateTimeInterval.from(2017, 9, 10, 12, 0) :: Nil)
    ).foreach(assert)
  }

  private def assert(testExpectations: (List[DateToken], List[DateTimeInterval])) = {
    DateTimeInterval.of(testExpectations._1) shouldBe testExpectations._2
  }
}


//"date with four letter month abbreviation + 2 different from time" in {
//assertDate("Sept. 10, 2016 11:00am, 3:00pm",
//LocalDateTime.of(2016, Month.SEPTEMBER, 10, 11, 0),
//LocalDateTime.of(2016, Month.SEPTEMBER, 10, 15, 0))