package uk.vitalcode.dateparser

import java.time.LocalDateTime

import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.{FreeSpec, _}


class Examples extends FreeSpec with Matchers {

  val year = LocalDateTime.now().getYear

  "should parse text into collection of date time intervals" in {

    "6 May 2017" should parseAs(List(
      DateTimeInterval.from(2017, 5, 6)
    ))
    "Saturday, January 9th 2017 from 3:00 PM" should parseAs(List(
      DateTimeInterval.from(2017, 1, 9, 15, 0)
    ))
    "Thursday, March 17th 2017 from 7:00 PM to 8:30 PM" should parseAs(List(
      DateTimeInterval.from(2017, 3, 17, 19, 0).to(2017, 3, 17, 20, 30)
    ))
    "Sept. 10, 2017 12:00pm" should parseAs(List(
      DateTimeInterval.from(2017, 9, 10, 12, 0)
    ))
    "Sept. 10, 2017 11:00am, 3:00pm" should parseAs(List(
      DateTimeInterval.from(2017, 9, 10, 11, 0),
      DateTimeInterval.from(2017, 9, 10, 15, 0)
    ))
    "Fri 24 Jun 6:45pm (doors) | 11pm (curfew)" should parseAs(List(
      DateTimeInterval.from(year, 6, 24, 18, 45).to(year, 6, 24, 23, 0)
    ))
    "Fri 24 Jun 6:45pm (doors) | 11pm (curfew)" should parseAs(List(
      DateTimeInterval.from(year, 6, 24, 18, 45).to(year, 6, 24, 23, 0)
    ))
  }

  private def parseAs(right: List[DateTimeInterval]): Matcher[String] = new Matcher[String] {
    def apply(left: String): MatchResult = {
      val actual = DateTimeInterval.of(left)
      MatchResult(
        actual == right,
        s"String [$left] results in [$actual] which is not the same as expected [$right]",
        s"String [$left] results in $right but it must not"
      )
    }
  }
}
