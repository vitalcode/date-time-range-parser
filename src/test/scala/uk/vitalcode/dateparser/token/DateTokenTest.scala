package uk.vitalcode.dateparser.token

import org.scalatest.{FreeSpec, ShouldMatchers}

class DateTokenTest extends FreeSpec with ShouldMatchers {

  "should parse text into corresponding date tokens" in {

    DateToken.parse("6 May 2017") shouldBe Day(6, 0) :: Month(5, 1) :: Year(2017, 2) :: Nil
  }
}
