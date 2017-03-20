package uk.vitalcode.dateparser

import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.{FreeSpec, _}


class Examples extends FreeSpec with ShouldMatchers {

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
  }

  private def parseAs(right: List[DateTimeInterval]): Matcher[String] = new Matcher[String] {
    def apply(left: String): MatchResult = {
      MatchResult(
        DateTimeInterval.of(left) == right,
        s"String [$left] does not result in [$right]",
        s"String [$left] results in $right but it must not"
      )
    }
  }
}

