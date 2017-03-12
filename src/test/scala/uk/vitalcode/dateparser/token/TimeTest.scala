package uk.vitalcode.dateparser.token

import java.time.LocalTime

import org.scalatest._
import org.scalatest.matchers.{MatchResult, Matcher}

class TimeTest extends FreeSpec with ShouldMatchers {

    "parsing string containing valid time" - {

      "create TimeToken for 24 hours time format string" in {
        "20:05:30" should beTimeToken(LocalTime.of(20, 5, 30))
        "20:05:30" should beTimeToken(LocalTime.of(20, 5, 30))
        "20:05" should beTimeToken(LocalTime.of(20, 5))
        "20:5" should beTimeToken(LocalTime.of(20, 5))
        "5:5" should beTimeToken(LocalTime.of(5, 5))
        "05:5" should beTimeToken(LocalTime.of(5, 5))
        "05:05" should beTimeToken(LocalTime.of(5, 5))
        "5:5:20" should beTimeToken(LocalTime.of(5, 5, 20))
        "5:5:05" should beTimeToken(LocalTime.of(5, 5, 5))
        "5:5:9" should beTimeToken(LocalTime.of(5, 5, 9))
      }

      "create TimeToken for 24 hours dot separated time format string" in {

        "20.05.30" should beTimeToken(LocalTime.of(20, 5, 30))
        "5.5" should beTimeToken(LocalTime.of(5, 5))
        "5.5.05" should beTimeToken(LocalTime.of(5, 5, 5))
      }

      "create TimeToken for 12 hours time format string" in {

        "08:05PM" should beTimeToken(LocalTime.of(20, 5))
        "08:05:25PM" should beTimeToken(LocalTime.of(20, 5, 25))
        "8:5:5PM" should beTimeToken(LocalTime.of(20, 5, 5))
        "08:5:5PM" should beTimeToken(LocalTime.of(20, 5, 5))
        "08:05:5PM" should beTimeToken(LocalTime.of(20, 5, 5))
        "08:5:05PM" should beTimeToken(LocalTime.of(20, 5, 5))
        "08:5:05AM" should beTimeToken(LocalTime.of(8, 5, 5))
        "12:0AM" should beTimeToken(LocalTime.of(0, 0))
        "12:00PM" should beTimeToken(LocalTime.of(12, 0))
        "08:05:25 PM" should beTimeToken(LocalTime.of(20, 5, 25))
        "08:05:25 pm" should beTimeToken(LocalTime.of(20, 5, 25))
        "8:5:5am" should beTimeToken(LocalTime.of(8, 5, 5))
        "11pm" should beTimeToken(LocalTime.of(23, 0))
        "6 AM" should beTimeToken(LocalTime.of(6, 0))
      }

      "create TimeToken for 12 hours dot separated time format string" in {

        "8.05.30 pm" should beTimeToken(LocalTime.of(20, 5, 30))
        "5.5 am" should beTimeToken(LocalTime.of(5, 5))
        "5.5.05PM" should beTimeToken(LocalTime.of(17, 5, 5))
      }

      "create TimeToken for time strings containing non-time related text" in {

        "Time:5.45pm" should beTimeToken(LocalTime.of(17, 45))
      }
    }
    "parsing strings that do not contain valid time" - {

      "not result in TimeToken" in {

        "November" should notBeTimeToken
        "12" should notBeTimeToken
        "Monday" should notBeTimeToken
      }
    }

  private def beTimeToken(right: LocalTime = null): Matcher[String] = new Matcher[String] {
    def apply(left: String): MatchResult = {
      val token = Time.of(left, 0)
      MatchResult(
        token.isSuccess && token.get.value == right,
        s"String [$left] does not result in TimeToken [$right]",
        s"String [$left] results in TimeToken [$right] but it shouldn't have"
      )
    }
  }

  private def notBeTimeToken(): Matcher[String] = new Matcher[String] {
    def apply(left: String): MatchResult = {
      val token = Time.of(left, 0)
      MatchResult(
        token.isFailure,
        s"String [$left] results in TimeToken [${token.getOrElse("")}]",
        s"String [$left] does not result in TimeToken but it should have"
      )
    }
  }
}

