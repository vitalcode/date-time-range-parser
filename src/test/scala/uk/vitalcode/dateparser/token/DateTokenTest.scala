package uk.vitalcode.dateparser.token

import java.time.{DayOfWeek, LocalTime}

import org.scalatest.{FreeSpec, ShouldMatchers}
import uk.vitalcode.dateparser.token.DateTokenTest.DateTokenParser

class DateTokenTest extends FreeSpec with ShouldMatchers {

  "should parse text into corresponding date tokens" in {

    parse"6 May 2017" shouldBe List(
      Day(6, 0), Month(5, 1), Year(2017, 2))

    parse"Saturday, January 9th 2017 from 3:00 PM" shouldBe List(
      WeekDay(DayOfWeek.SATURDAY, 0), Month(1, 1), Day(9, 2),  Year(2017, 3), Time(LocalTime.of(15, 0), 4))
  }
}

object DateTokenTest {

  implicit class DateTokenParser(val sc: StringContext) extends AnyVal {
    def parse(args: Any*): List[DateToken] =
      DateToken.parse(sc.s(args: _*))
  }
}