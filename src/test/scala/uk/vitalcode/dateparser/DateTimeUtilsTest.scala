package uk.vitalcode.dateparser

import java.time.{DayOfWeek, LocalDate, LocalDateTime}

import org.scalatest._
import uk.vitalcode.dateparser.DateTimeUtils.datesInRange

class DateTimeUtilsTest extends FreeSpec with ShouldMatchers {

  "datesInRange util" - {
    "date range: [2016-01-01 to 2016-01-10]" - {
      "has 10 dates" in {
        datesInRange(
          LocalDate.of(2016, 1, 1),
          LocalDate.of(2016, 1, 10)
        ) shouldBe List(
          LocalDate.of(2016, 1, 1),
          LocalDate.of(2016, 1, 2),
          LocalDate.of(2016, 1, 3),
          LocalDate.of(2016, 1, 4),
          LocalDate.of(2016, 1, 5),
          LocalDate.of(2016, 1, 6),
          LocalDate.of(2016, 1, 7),
          LocalDate.of(2016, 1, 8),
          LocalDate.of(2016, 1, 9),
          LocalDate.of(2016, 1, 10)
        )
      }
    }
    "date range: [2016-02-26 to 2016-03-02]" - {
      "has 5 dates" in {
        datesInRange(
          LocalDate.of(2016, 2, 26),
          LocalDate.of(2016, 3, 2)
        ) shouldBe List(
          LocalDate.of(2016, 2, 26),
          LocalDate.of(2016, 2, 27),
          LocalDate.of(2016, 2, 28),
          LocalDate.of(2016, 2, 29),
          LocalDate.of(2016, 3, 1),
          LocalDate.of(2016, 3, 2)
        )
      }
    }
    "date range: [2016-07-27 to 2016-08-08] and days of the week: [Tuesday, Saturday, Sunday] " - {
      "return 5 dates" in {
        datesInRange(
          LocalDate.of(2016, 7, 27),
          LocalDate.of(2016, 8, 8),
          Set(DayOfWeek.TUESDAY, DayOfWeek.SATURDAY, DayOfWeek.SUNDAY)
        ) shouldBe List(
          LocalDate.of(2016, 7, 30),
          LocalDate.of(2016, 7, 31),
          LocalDate.of(2016, 8, 2),
          LocalDate.of(2016, 8, 6),
          LocalDate.of(2016, 8, 7)
        )
      }
    }
    "date range: [2016-01-01 to 2016-01-04] and days of the week: [Monday, Tuesday, Friday, Sunday]" - {
      "return 3 dates" in {
        datesInRange(
          LocalDate.of(2016, 1, 1),
          LocalDate.of(2016, 1, 4),
          Set(DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.FRIDAY, DayOfWeek.SUNDAY)
        ) shouldBe List(
          LocalDate.of(2016, 1, 1),
          LocalDate.of(2016, 1, 3),
          LocalDate.of(2016, 1, 4)
        )
      }
    }
  }
  "getYear util" - {
    "should get correct next event year" in {
      val currentYear = LocalDateTime.now.getYear
      DateTimeUtils.getYear(5, 6) shouldBe currentYear
    }
  }
}
