package uk.vitalcode.dateparser

import java.time.LocalDateTime

import org.scalamock.scalatest.MockFactory
import org.scalatest.matchers.{MatchResult, Matcher}
import org.scalatest.{FreeSpec, _}


trait ExamplesSupport extends FreeSpec with Matchers with MockFactory {

  protected val currentDate: LocalDateTime

  protected val examples: List[(String, List[DateTimeInterval])]

  private val dateTimeProvider = new DateTimeProvider {
    override def now: LocalDateTime = currentDate
  }

  protected def parseAs(right: List[DateTimeInterval]): Matcher[String] = new Matcher[String] {
    def apply(left: String): MatchResult = {
      val actual = DateTimeInterval.of(left, dateTimeProvider)
      MatchResult(
        actual == right,
        s"String [$left] results in [$actual] which is not the same as expected [$right]",
        s"String [$left] results in $right but it must not"
      )
    }
  }

  protected def run() = {
    "should parse text as date time intervals" - {
      examples.foreach {
        case (textToParse, expectation) => {
          textToParse in {
            textToParse should parseAs(expectation)
          }
        }
      }
    }
  }
}
