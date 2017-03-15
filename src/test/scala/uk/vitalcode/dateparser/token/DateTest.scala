package uk.vitalcode.dateparser.token

import java.time.LocalDate

import org.scalatest.{FreeSpec, ShouldMatchers}

class DateTest extends FreeSpec with ShouldMatchers {

  "Should correctly initialise Date token" in {
    Date(2017, 5, 6, 10) shouldBe Date(LocalDate.of(2017, 5, 6), 10)
  }
}
