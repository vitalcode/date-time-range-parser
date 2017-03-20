package uk.vitalcode.dateparser

import java.time.LocalDateTime

import org.scalatest._
import org.scalatest.matchers.{MatchResult, Matcher}


trait ExamplesSupport extends fixture.FreeSpec with fixture.TestDataFixture with Matchers {

  protected val currentDate: LocalDateTime

  private val dateTimeProvider = new DateTimeProvider {
    override def now: LocalDateTime = currentDate
  }

  private def parseAs(right: List[DateTimeInterval]): Matcher[String] = new Matcher[String] {
    def apply(left: String): MatchResult = {
      val actual = DateTimeInterval.of(left, dateTimeProvider)
      MatchResult(
        actual == right,
        s"String [$left] results in [$actual] which is not the same as expected [$right]",
        s"String [$left] results in $right but it must not"
      )
    }
  }

  protected def expected(expectation: DateTimeInterval*) =
    (text: FixtureParam) => text.name should parseAs(expectation.toList)
}
