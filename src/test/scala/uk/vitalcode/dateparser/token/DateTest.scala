package uk.vitalcode.dateparser.token

import java.time.LocalDate

import org.scalatest.{FreeSpec, ShouldMatchers}

class DateTest extends FreeSpec with ShouldMatchers {

  "should correctly initialise date token when provided valid year, month and day of the date" in {
    Date(2017, 5, 6, 10).get shouldBe Date(LocalDate.of(2017, 5, 6), 10)
    Date(2017, 5, 31).get shouldBe Date(LocalDate.of(2017, 5, 31), 0)
  }

  "should fail to initialise date token with combination of year, month and day values that results in invalid date" in {
    Date(2017, 5, 32, 10).isFailure shouldBe true
    Date(2017, 13, 20).isFailure shouldBe true
  }
}
